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
        FileContext.writer.write("""    <div class="item-in-cart-preview" v-for="article in articles" :key="article.name" >\n""")
        if(displayImage){
            FileContext.writer.write(""" <vaadin-horizontal-layout>\n       <img v-bind:src="article.url"/>\n""")
        }
        FileContext.writer.write("""<vaadin-vertical-layout theme="spacing padding">\n""")
        if(displayPrice){
            FileContext.writer.write("""         <vaadin-number-field v-bind:value="article.price" readonly>
              <div slot="suffix">€</div>
            </vaadin-number-field>\n""")
        }
        if(displayQuantity){
            FileContext.writer.write("""        <span class="quantity">Qty:  {{article.quantity}}Pcs</span>\n""")
        }
        FileContext.writer.write("""</vaadin-vertical-layout>\n""")
        FileContext.writer.write(""" </vaadin-horizontal-layout>   </div>\n""")
    }

}
