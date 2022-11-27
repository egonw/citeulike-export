# citeulike-export

This repository contains CiteULike (RIP) data and code to mash it up with Wikidata.

# Data sources

## egonw archive

The two BibTeX exports of the 'egonw' account on CiteULike are stored as `egonw.bib`
and `citingMe.bib`.

To extract information from the `egonw.bib` BibTeX file a Groovy scripts is used,
of which the output can be validated in the same oneliner:

```shell
groovy extractDOIs.groovy | tee egonw.ttl | rapper -i turtle -t -q - . > /dev/null
```

## CiteULike pages

Because the CiTO annotation turns out to now show up as keywords in the BibTeX files,
copies of the CDK papers were restored from the Internet Archive: `article*.html`.

To extract information from the CiteULike HTML files a Groovy scripts is used,
of which the output can be validated in the same oneliner:

```shell
groovy extractCITO.groovy | tee cul.ttl | rapper -i turtle -t -q - . > /dev/null
```

## Wikidata

```shell
groovy createWikidataQuery.groovy | tee wikidata.rq
curl -H "Accept: text/tab-separated-values" --data-urlencode query@wikidata.rq https://query.wikidata.org/bigdata/namespace/wdq/sparql -o wikidata.tsv
```


