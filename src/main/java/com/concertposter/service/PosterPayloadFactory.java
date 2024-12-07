package com.concertposter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PosterPayloadFactory implements PayloadFactory {

    @Override
    public String createPayload(String base64Image) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Content section: text and image
            ObjectNode textNode = mapper.createObjectNode();
            textNode.put("type", "text");
            textNode.put("text", """
                ""\"
                                    Example JSON: { \\
                                        "date": "2023-10-25", \\
                                        "venue": { \\
                                            "db_name": "d3_arts", \\
                                            "stylized_name": "D3 Arts", \\
                                            "address_protected": false, \\
                                            "address": "3632 Morrison Road", \\
                                            "website": "d3arts.org" \\
                                        }, \\
                                        "cost": "0.00", \\
                                        "doors_time": "17:00:00", \\
                                        "show_start_time": "17:00:00", \\
                                        "age_requirement": 0, \\
                                        "description": "", \\
                                        "title": null, \\
                                        "bands": [ \\
                                            { \\
                                                "db_name": "jazz_morillo", \\
                                                "stylized_name": "Jazz Morillo", \\
                                                "genre": "rock", \\
                                                "biography": "Jazz Morillo is a great singer, how awesome.", \\
                                                "website": "jazzmorillo.com", \\
                                                "music_link": "spotify.com/jazz_morillo", \\
                                                "contact_email": "jazzmorillo@gmail.com", \\
                                                "instagram_username": "@jazzmorillo", \\
                                                "twitter_username": "@jazzmorillo", \\
                                                "tiktok_username": "@jazzmorillo", \\
                                                "is_active": true \\
                                            } \\
                                        ] \\
                                    } \\
                                    Based on this concert poster can you find information like that provided in the example json, and \\
                                    return it in an identical structure. I'll break down each parameter so its easier for you to find. \\
                                   \s
                                    "date" is the date of the event
                                                "venue" is a json object that contains data about the venue the show is being played at. It contains\s
                                                these keys.
                                                    {
                                                    "db_name" the name of the venue in a format without spaces and lowercase
                                                        "stylized_name" the name of the venue as found on the poster
                                                        "address_protected" a boolean representing whether or not the address is publicly available. If the poster says "DM for address" it is likely not publically available. true means the address is not publicly available,
                                                    false means it is
                                                        "address" the address of the venue
                                                        "website" the venues website
                                                }
                                                "cost" is a number representing how much the event costs, if its not listed, assume free
                                                "doors_time" is the time doors open, it should be around a part of the poster that says doors
                                                "show_start_time" is the time the show starts. if there's only one time on the poster, its likely this one. If there are two, it will be the later one
                                                "age_requirement" is how old you have to be to get into the event it should be encoded as such (all ages/not listed -> 0,
                                                16+ -> 16,
                                                18+ -> 18,
                                                21+ -> 21)
                                                "description" a description or tagline of the event if provided
                                                "title" the name of the event if provided
                                                "bands" is a json object representing the bands that are playing the show. It contains these keys.
                                                    {
                                                    "db_name" name of band without spaces and lowercase
                                                        "stylized_name" name of band as written on poster
                                                        "genre" genre of music that the band plays, for this parameter only it is ok to infer from the poster style. If that isn't possible and empty string is also fine
                                                        "biography" description of the band
                                                        "website" bands website
                                                        "music_link" a link pointing to where the bands music is (spotify, apple music, etc)
                                                        "contact_email" an email to contact the band, if its not close the band name, its likely another email and should not be inputted here
                                                        "instagram_username" the bands instagram username
                                                        "twitter_username" the bands twitter username
                                                        "tiktok_username" the bands tiktok username
                                                        "is_active" whether the band is still playing shows
                                                }
                                   \s
                                    This is an ambitious task so if certain data points aren't available, \\
                                    it's acceptable to leave them blank (empty strings for strings and 0s for numbers). \\
                                    Just make sure the structure is identical to the input. \\
                                    If the image appears to not be a concert poster, just return an empty JSON. \\
                                    And in your response, only print the JSON. Thank you!
                                ""\"
            """);

            ObjectNode imageNode = mapper.createObjectNode();
            imageNode.put("type", "image_url");
            ObjectNode imageUrlNode = mapper.createObjectNode();
            imageUrlNode.put("url", "data:image/jpeg;base64," + base64Image);
            imageNode.set("image_url", imageUrlNode);

            // Messages array
            ArrayNode messagesArray = mapper.createArrayNode();
            ObjectNode messageNode = mapper.createObjectNode();
            messageNode.put("role", "user");

            ArrayNode contentArray = mapper.createArrayNode();
            contentArray.add(textNode);
            contentArray.add(imageNode);
            messageNode.set("content", contentArray);

            messagesArray.add(messageNode);

            // Final payload
            ObjectNode payload = mapper.createObjectNode();
            payload.put("model", "gpt-4o-mini");
            payload.set("messages", messagesArray);
            payload.put("max_tokens", 5000);

            return mapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Error constructing payload: " + e.getMessage(), e);
        }
    }
}