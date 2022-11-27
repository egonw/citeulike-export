println "PREFIX wdt: <http://www.wikidata.org/prop/direct/>"
println "prefix wd:  <http://www.wikidata.org/entity/>"
println ""

file = new File("wikidata.tsv")

file.eachLine { line ->
  if (line.contains("http://www.wikidata.org/entity/")) {
    parts = line.split("\t")
    wd = parts[0].replace("<http://www.wikidata.org/entity/", "wd:").replace(">", "")
    doi = parts[1]
    println "${wd} wdt:P356 ${doi} ."
  }
}
