package com.micodes.sickleraid.data.datasource.api.callUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

// Function to fetch HTML content from a URL and return it as a string
fun fetchHtmlContent(url: String): String {
    return Jsoup.connect(url).get().html()
}

// Function to scrape educational materials from the website
fun scrapeEducationalMaterials(html: String): List<Pair<String, String>> {
    val doc: Document = Jsoup.parse(html)
    val educationalMaterials = mutableListOf<Pair<String, String>>()

    // Extracting educational materials
    val items = doc.select("h2:contains(Educational Materials) + ul > li > a")
    for (item in items) {
        val link = item.attr("href")
        val title = item.text()
        educationalMaterials.add(Pair(title, link))
    }

    return educationalMaterials
}

// Example usage
fun main() {
    val url = "https://www.sicklecelldisease.org/support-and-community/links-resources/"
    val html = fetchHtmlContent(url)
    val educationalMaterials = scrapeEducationalMaterials(html)
    educationalMaterials.forEachIndexed { index, (title, link) ->
        println("${index + 1}. $title: $link")
    }
}

