package uxifier.models

import uxifier.vue.project.models.VueCartActionMenu
import uxifier.vue.project.models.VueCartPreview
import uxifier.vue.project.models.VueCartPreviewArticle

class CartPreview implements Component{
    boolean displayTotal
    ArticleInCartPreview articleInCartPreview

    @Override
    def buildVue() {
        var vueCartPreview = new VueCartPreview()
        vueCartPreview.displayTotal = this.displayTotal

        vueCartPreview.setArticleInCartPreview(new VueCartPreviewArticle(this.articleInCartPreview))
        return vueCartPreview
    }

}
