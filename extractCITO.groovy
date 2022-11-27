println "prefix citeulike: <http://www.citeulike.org/article/>"
println "prefix wdt: <http://www.wikidata.org/entity/>"
println "prefix cito: <http://purl.org/spar/cito/>"
println ""

artId = "423382"
file = new File("article_${artId}.html")

file.eachLine { line ->
  if (line.contains("meta property=\"cito:")) {
    parts = line.split("\"")
    art = parts[1]
    line = parts[3].replace("http://www.citeulike.org/article/", "citeulike:")
    println "citeulike:${artId} ${art} ${line} ."
  }
}
