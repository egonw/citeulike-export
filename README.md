# citeulike-export

This repository contains CiteULike (RIP) data and code to mash it up with Wikidata.

# Data sources

## egonw archive

The two BibTeX exports of the 'egonw' account on CiteULike are stored as `egonw.bib`
and `citingMe.bib`.

## CiteULike pages

Because the CiTO annotation turns out to now show up as keywords in the BibTeX files,
copies of the CDK papers were restored from the Internet Archive: `article*.html`.

# Turtle generation

To extract information from the BibTeX files and CiteULike HTML pages two Groovy
scripts are used, of which the output can be validated in the same oneliner:

```shell
groovy extractCITO.groovy | tee cul.ttl | rapper -i turtle -t -q - . > /dev/null
```

The `extractCITO.groovy` script needs to be adapted for each HTML page.
