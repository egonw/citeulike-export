println "prefix citeulike: <http://www.citeulike.org/article/>"
println "prefix wdt: <http://www.wikidata.org/prop/direct/>"
println ""

file = new File("egonw.bib")

file.eachLine { line ->
  if (line.contains("citeulike-article-id")) {
    line = line.replace("    citeulike-article-id = {", "").replace("},", "")
    art = "citeulike:${line}"
  } else if (line.contains("doi =")) {
    line = line.replace("    doi = {", "").replace("},", "")
    println "${art} wdt:P356 \"${line}\" ."
    art = ""
  }
}
