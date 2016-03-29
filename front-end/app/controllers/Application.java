package controllers;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.Base64.Encoder;
import java.util.stream.Stream;

import com.google.common.collect.Maps;

import play.mvc.Controller;
import play.mvc.Result;
import worldwonders.wonders.Comment;
import worldwonders.wonders.ImageInfo;
import worldwonders.wonders.Wonder;
import worldwonders.wonders.WonderType;

public class Application extends Controller {
    Wonder colossus() {
        try {
        return new Wonder()
            .setEnglishName("Colossus of Rhodes")
            .setNativeNames(Arrays.asList(
                    "ὁ Κολοσσὸς Ῥόδιος"))
            .setAverageRating(3.5)
            .setDescription("The Colossus of Rhodes was a statue of the Greek titan-god of the sun Helios, " +
                    "erected in the city of Rhodes, on the Greek island of the same name, by Chares of Lindos in 280 BC. " +
                    "One of the Seven Wonders of the Ancient World, it was constructed to celebrate Rhodes' victory over the ruler of Cyprus, " +
                    "Antigonus I Monophthalmus, whose son unsuccessfully besieged Rhodes in 305 BC. Before its destruction in the earthquake " +
                    "of 226 BC, the Colossus of Rhodes stood over 30 metres (98 feet) high, making it one of the tallest statues of the ancient world.")
            .setImageInfo(
                    new ImageInfo()
                        .setImageLink(new URI(
                            "/image/"+ Base64.getEncoder().encodeToString(
                            "http://img06.deviantart.net/a7d0/i/2012/051/7/7/colossus_of_rhodes_by_pervandr-d4qcdti.jpg"
                                    .getBytes("UTF-8"))))
                        .setDoubleWidth(false)
                        .setDoubleHeight(true)
                        )
            .setTotalRatings(4)
            .setWonderType(WonderType.Ancient)
            .setChosenComments(Arrays.asList(
                    new Comment()
                    .setBody("I saw it, and it didn't look like this. It was standing next to the harbor entrance, not above it")
                    .setRating(1)
                    .setUser("john@timetravel.com")
                    .setCreatedAt(OffsetDateTime.parse("-0240-04-01T15:29:01+02:00")),
                    new Comment()
                    .setBody("This thing was an inspiration for the Statue of Liberty!!1")
                    .setRating(5)
                    .setUser("cpt.obvious@example.org")
                    .setCreatedAt(OffsetDateTime.parse("2015-02-01T13:29:01+02:00"))

            ));
        }catch(final Exception e){
            throw new RuntimeException(e);
        }
    }

    public Result details(final String wonderName) {
        return ok(views.html.details.render(colossus(),
                Arrays.asList(
                        new worldwonders.comments.Comment()
                        .setBody("helo1")
                        .setRating(1)
                        .setTopic("aoeu"),
                        new worldwonders.comments.Comment()
                        .setBody("helo3")
                        .setRating(3)
                        .setTopic("aoeu"),
                        new worldwonders.comments.Comment()
                        .setBody("helo5")
                        .setRating(5)
                        .setTopic("aoeu")
                        )));
    }

    public Result index() {
        final List<WonderType> wonderTypes =
                Arrays.asList(WonderType.values());

        return ok(views.html.index.render(wonderTypes,
                Arrays.asList(colossus())
                ));
    }
}
