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
    def insertSelfInStyle() {
        FileContext.writer.write("""
.cart-preview {
  position: absolute;
  right: 0px;
  z-index: 999;
  border-radius: 3px;
  background: white;
  padding: 30px;
  height: 80%;
  text-transform: none;
  border: 1px solid #eee;
}
.item-in-cart-preview {
  margin-bottom: 4px;
  border: 1px solid #eee;
  width: 100%;
}""")
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInData() {
        FileContext.writer.write("""
articles : [],                                       
""")
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""<div class="cart-preview" v-show="upHere">
                                            <vaadin-vertical-layout >
""")
        if(articleInCartPreview != null){
            articleInCartPreview.insertInTemplate()
        }
        FileContext.writer.write("""</vaadin-vertical-layout>\n""")
        FileContext.writer.write("  <span> Subtotal : {{subtotal}} </span>\n")
        FileContext.writer.write("""</div>\n""")
    }
}
