@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='0.1.2')

import java.text.SimpleDateFormat;
import java.util.Date;

workspaceRoot = "."
rdf = new net.bioclipse.managers.RDFManager(workspaceRoot);

base = rdf.createInMemoryStore();

rdf.importFile(base, "/cul.ttl", "TURTLE");
rdf.importFile(base, "/egonw.ttl", "TURTLE");
rdf.importFile(base, "/wikidata.ttl", "TURTLE");

mappings = new HashMap()
mappings.put("cito:obtainsSupportFrom",      "Q115470993")
mappings.put("cito:extends",                 "Q96472100")
mappings.put("cito:isExtendedBy",            "Q96472100") // inverse intention
mappings.put("cito:definesMethodUsedBy",     "Q96472102") // inverse intention
mappings.put("cito:isDiscussedBy",           "Q96471822") // inverse intention
mappings.put("cito:isCitedAsAuthorityBy",    "Q96479983") // inverse intention
mappings.put("cito:isCitedForInformationBy", "Q96479970") // inverse intention

sparql = """
PREFIX citeulike: <http://www.citeulike.org/article/>
PREFIX wdt: <http://www.wikidata.org/prop/direct/>
PREFIX cito: <http://purl.org/spar/cito/>

SELECT ?wd1 ?intention ?wd2 ?cul1 ?cul2 WHERE {
  {
    VALUES ?intention { cito:extends cito:obtainsSupportFrom }
    ?cul1 ?intention ?cul2 ; wdt:P356 ?doi1 .
    ?cul2 wdt:P356 ?doi2 .
  } UNION {
    VALUES ?intention { cito:definesMethodUsedBy cito:isExtendedBy cito:isDiscussedBy cito:isCitedAsAuthorityBy }
    # invert these intentions
    ?cul2 ?intention ?cul1 ; wdt:P356 ?doi2 .
    ?cul1 wdt:P356 ?doi1 .
  }
  ?wd1 wdt:P356 ?doi1 . FILTER(regex(str(?wd1), "wikidata"))
  ?wd2 wdt:P356 ?doi2 . FILTER(regex(str(?wd2), "wikidata"))
}
"""

String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
println "qid,P2860,qal3712,S248,s813,#"

results = rdf.sparql(base, sparql)
for (i=1;i<=results.rowCount;i++) {
  wd1 = results.get(i, "wd1").replace("http://www.wikidata.org/entity/", "")
  wd2 = results.get(i, "wd2").replace("http://www.wikidata.org/entity/", "")
  cul1 = results.get(i, "cul1").replace("citeulike:", "")
  cul2 = results.get(i, "cul2").replace("citeulike:", "")
  intention = results.get(i, "intention")
  wdIntention = mappings.get(intention)
  println "${wd1},${wd2},${wdIntention},Q115470140,+${date}T00:00:00Z/1,${cul1} ${intention} ${cul2}"
}
