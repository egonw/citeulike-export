println "PREFIX wdt: <http://www.wikidata.org/prop/direct/>"
println ""

file = new File("egonw.bib")

println "SELECT ?work ?doi WHERE {"
println "  VALUES ?doi {"
file.eachLine { line ->
  if (line.contains("doi =")) {
    line = line.replace("    doi = {", "").replace("},", "").toUpperCase()
    line = line.replace("\\%", "%")
    line = line.replace("\\_", "_")
    println "    \"${line}\""
  }
}
println "  }"
println "  ?work wdt:P356 ?doi ."
println "}"
