package uxifier.vue.project.models

import uxifier.models.ArticleInCartPreview

class VueCartPreviewArticle implements VueGeneratable{
    boolean  displayImage = false
    boolean  displayPrice = false
    boolean  displayQuantity = false
    boolean  allowRemoval = false

    VueCartPreviewArticle(ArticleInCartPreview articleInCartPreview){
        this.displayQuantity = articleInCartPreview.displayQuantity
        this.displayPrice = articleInCartPreview.displayPrice
        this.displayImage = articleInCartPreview.displayImage
        this.allowRemoval = articleInCartPreview.allowRemoval
    }
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
        FileContext.writer.write("""    <div v-for="article in articles">""")
        if(displayImage){
            FileContext.writer.write("""        <img :src="article.imageUrl"/>""")
        }
        if(displayPrice){
            FileContext.writer.write("""        <span> {{article.price}} </span>""")
        }
        if(displayQuantity){
            FileContext.writer.write("""        <span class="quantity">Qty:  {{article.quantity}}Pcs</span>""")
        }
        FileContext.writer.write("""    </div> """)
    }

}
