### What?
A lemmatizer for English and French.

### How to use it?

- call it directly in your code:
```java
Lemmatizer lemmatizer = new Lemmatizer("en");
String result = lemmatizer.sentenceLemmatizer("Students are in Paris");
// result: "Student are in Paris"
```

### Pros
- light weight: one single file
- two small dependencies with no further sub-dependencies for a __total of 200kb__

### What for?
This function is developed by Clement Levallois, in support of academic work published [in various places](https://scholar.google.fr/citations?user=r0R0vekAAAAJ&hl=en). It is now used in support of [a web app providing free text analysis for non coders](https://nocodefunctions.com).

### Contributions
Your contributions are very welcome.

### License
Apache v2
