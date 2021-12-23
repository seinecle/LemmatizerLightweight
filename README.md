### What?
A lemmatizer for English and French.

### How to use it?

- call it directly in your code:
```java
Lemmatizer lemmatizer = new Lemmatizer("en");
String result = lemmatizer.sentenceLemmatizer("Students are in Paris");
// result: "Student are in Paris"
```

- use it as a REST service:

Place the jar and the lib/ folder on your disk and launch the jar with:

```java
nohup java -jar LemmatizerLightweight-1.0.jar net.clementlevallois.lemmatizerlightweight.api.ApiController &
```
Now this service can be called with a POST request sent to:

http://localhost:7000/lemmatize/fr for French texts

or

http://localhost:7000/lemmatize/en for English texts

 where the payload is a JSON Object. Each value in the key-value pair will be lemmatized and returned as a JSON Object with the same keys.



### Pros
- light weight: one single file and two small dependencies for a __total of 200kb__
- an extra dependency (Javalin) to make it available as a REST service. Easy to remove if you don't need it.

### What for?
This function is developed by Clement Levallois, in support of academic work published [in various places](https://scholar.google.fr/citations?user=r0R0vekAAAAJ&hl=en). It is now used in support of [a web app providing free text analysis for non coders](https://nocodefunctions.com).

### Contributions
Your contributions are very welcome.

### License
Apache v2
