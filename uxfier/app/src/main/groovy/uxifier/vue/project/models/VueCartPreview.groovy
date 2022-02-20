package uxifier.vue.project.models

import uxifier.models.ArticleInCartPreview

class VueCartPreview implements VueGeneratable{
    boolean displayTotal
    VueCartPreviewArticle articleInCartPreview
    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""<div v-show="upHere">""")
        if(articleInCartPreview != null){
            articleInCartPreview.insertInTemplate()
        }
        FileContext.writer.write("  <span> Subtotal : {{subtotal}}")
        FileContext.writer.write("""</div>""")
    }
}
