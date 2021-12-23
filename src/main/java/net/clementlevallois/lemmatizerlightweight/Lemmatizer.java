/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.lemmatizerlightweight;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.clementlevallois.stopwords.Stopwords;

/**
 *
 * @author LEVALLOIS
 */
public class Lemmatizer {

    private String[] noLemmaEN = new String[]{"access", "accumbens", "addresses", "afterwards", "always", "amazing", "approaches", "analyses", "biases", "businesses", "ceiling", "classes", "crises", "daunting", "discusses", "economics", "ethics", "focuses", "fries", "goes", "humanities", "hypotheses", "inches", "lies", "losses", "marketing", "morning", "news", "outstanding", "physics", "politics", "premises", "processes", "red", "rigged", "ries", "series", "sometimes", "species", "spring", "status", "ted", "themselves", "neural processes", "united", "wales", "witnesses"};
    private String[] noLemmaFR = new String[]{"accès", "alors", "après", "auprès", "concours", "corps", "cours", "dans", "discours", "divers", "êtes", "éthos", "gros","lors", "outils", "pays", "parcours", "près", "procès", "puis", "sans", "secours", "sens", "sommes", "succès", "temps", "travers", "très", "univers", "viens"};
    private String[] noLemma = new String[]{"analytics", "accumbens", "aws", "bayes", "business", "charles", "ects", "cnrs", "cowles", "deep learning", "developer", "ethos", "faas", "forbes", "iaas", "james", "keynes", "koopmans", "nhs", "paas", "paris", "programming", "reactjs", "saas", "siemens", "sanders", "ted", "vuejs", "united states"};

    // el/elle e/é i/î ie/ique al/ale
    private Set<String> noLemmaSet;
    private final String lang;

    public static void main(String[] args) throws Exception {
        Lemmatizer l = new Lemmatizer("en");
        l.lemmatize("gets");
    }

    public Lemmatizer(String lang) throws Exception {
        switch (lang) {
            case "en":
                noLemmaSet = new HashSet(Arrays.asList(noLemmaEN));
                break;
            case "fr":
                noLemmaSet = new HashSet(Arrays.asList(noLemmaFR));
                break;
            default:
                noLemmaSet = new HashSet();
                break;
        }
        noLemmaSet.addAll(Stopwords.getStopWords(lang).get("long"));
        noLemmaSet.addAll(Arrays.asList(noLemma));
        this.lang = lang;
    }

    public String lemmatize(String term) {

        String currEntry = term.toLowerCase();
        String[] terms = currEntry.split(" ");
        String lastTerm = terms[terms.length - 1];
        if (noLemmaSet.contains(lastTerm)) {
            return currEntry.trim();
        }

        if ((currEntry.endsWith("s") | currEntry.endsWith("s'"))
                && !currEntry.endsWith("us")
                && !currEntry.endsWith("as")
                && !currEntry.endsWith("ss")
                && !currEntry.endsWith("ies")
                && !noLemmaSet.contains(currEntry)
                && !currEntry.endsWith("is")) {
            if (currEntry.endsWith("s")) {
                currEntry = currEntry.substring(0, currEntry.length() - 1);
            }
            if (currEntry.endsWith("s'")) {
                currEntry = currEntry.substring(0, currEntry.length() - 2);
            }

        } else if (currEntry.endsWith("'")) {
            currEntry = currEntry.substring(0, currEntry.length() - 1);
        }

        if (lang.equals("en")) {
            if (currEntry.endsWith("ies")) {
                currEntry = currEntry.substring(0, currEntry.length() - 3) + "y";
            } else if (currEntry.endsWith("'s")) {
                currEntry = currEntry.substring(0, currEntry.length() - 2);
            } else if (currEntry.endsWith("ed")) {
                if (currEntry.endsWith("lked") || currEntry.endsWith("cked") || currEntry.endsWith("ssed") || currEntry.endsWith("lled") || currEntry.endsWith("lled") || currEntry.endsWith("red") || currEntry.endsWith("ned") || (currEntry.endsWith("ted") & !currEntry.endsWith("ated"))) {
                    currEntry = currEntry.substring(0, currEntry.length() - 2);
                }
                else if (currEntry.endsWith("ied")) {
                    currEntry = currEntry.substring(0, currEntry.length() - 3) + "y";
                }
                else if (currEntry.endsWith("rred")) {
                    currEntry = currEntry.substring(0, currEntry.length() - 3);
                }
                else {
                    // purchased -> purchase
                    currEntry = currEntry.substring(0, currEntry.length() - 1);
                }
            } else if (currEntry.endsWith("'s")) {
                currEntry = currEntry.substring(0, currEntry.length() - 2);
            } else if (currEntry.endsWith("ing")) {
                if (currEntry.endsWith("king") && !currEntry.equals("king")) {
                    currEntry = currEntry.substring(0, currEntry.length() - 3) + "e";
                } else if (currEntry.endsWith("sing") || currEntry.endsWith("cing")|| currEntry.endsWith("ving")|| currEntry.endsWith("ring")) {
                    currEntry = currEntry.substring(0, currEntry.length() - 3) + "e";
                } else if (currEntry.length() > 2) {
                    currEntry = currEntry.substring(0, currEntry.length() - 3);
                    //running has become runn. Should become run.
                    int size = currEntry.length();
                    if (size > 1) {
                        String lastTwoLetters = currEntry.substring(size - 2, size);
                        if (lastTwoLetters.length() > 1 && lastTwoLetters.charAt(0) == lastTwoLetters.charAt(1)) {
                            currEntry = currEntry.substring(0, currEntry.length() - 1);
                        }
                        size = currEntry.length();
                        if (size > 1) {
                            //voting has become vot. Should become vote. Same for any word ending in at or ot (not plant, suspect, ...). Yet, it will miscorrect "pivot"
                            lastTwoLetters = currEntry.substring(size - 2, size);
                            if (lastTwoLetters.equals("at") || lastTwoLetters.equals("ot")) {
                                currEntry = currEntry + "e";
                            }
                        }
                    }
                }
            } else if (currEntry.endsWith("ier")) {
                currEntry = currEntry.substring(0, currEntry.length() - 3) + "y";
            }
        }
        if (lang.equals("fr")) {
            if (currEntry.endsWith("ère")) {
                currEntry = currEntry.substring(0, currEntry.length() - 3) + "er";
            }
            if (currEntry.endsWith("rions")) {
                currEntry = currEntry.substring(0, currEntry.length() - 5);
            }
            // more work to do on French conjuguaisons obviously - doable! Get in touch via Github issues.
        }
        return currEntry.trim();

    }

    public String sentenceLemmatizer(String sentence) {

        String[] terms = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String term : terms) {
            sb.append(lemmatize(term));
            sb.append(" ");
        }
        return sb.toString().trim();
    }

}
