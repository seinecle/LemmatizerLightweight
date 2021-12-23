/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.lemmatizerlightweight.api;

/**
 *
 * @author LEVALLOIS
 */
import io.javalin.Javalin;
import java.io.StringReader;
import java.util.Iterator;
import java.util.TreeMap;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import net.clementlevallois.lemmatizerlightweight.Lemmatizer;

public class ApiController {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.post("/lemmatize/{lang}", ctx -> {
            TreeMap<String, String> lines = new TreeMap();
            String body = ctx.body();
            if (body.isEmpty()) {
                ctx.status(500);
            } else {
                JsonReader jsonReader = Json.createReader(new StringReader(body));
                JsonObject jsonObject = jsonReader.readObject();
                Iterator<String> iteratorKeys = jsonObject.keySet().iterator();
                Lemmatizer lemmatizer = new Lemmatizer(ctx.pathParam("lang"));
                while (iteratorKeys.hasNext()) {
                    String nextKey = iteratorKeys.next();
                    String line = jsonObject.getString(nextKey);
                    String sentenceLemmatized = lemmatizer.sentenceLemmatizer(line);
                    lines.put(nextKey, sentenceLemmatized);
                }
                ctx.json(lines);
            }
        });
    }
}
