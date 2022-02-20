package uxifier

import uxifier.models.ArticleInCartPreview
import uxifier.models.CartPreview

class CartPreviewBuilder implements GenericBuilder{
    CartPreview cartPreview = new CartPreview()

    def Article(@DelegatesTo(value = ArticleBuilder, strategy = Closure.DELEGATE_FIRST) Closure closure){
        var builder = new ArticleBuilder()
        var code = closure.rehydrate(builder, this,this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()

        cartPreview.articleInCartPreview=builder.buildArticle()
    }

    def missingProperty(String propertyName){
        if("displayTotal".equals(propertyName)){
            this.cartPreview.displayTotal = true
        }
        else{
            println("Property unknown ${propertyName}")
        }
    }

    def buildPreview(){
        return this.cartPreview
    }
}

class ArticleBuilder implements GenericBuilder{
    ArticleInCartPreview articleInCartPreview=new ArticleInCartPreview()

    ArticleInCartPreview buildArticle(){
        return this.articleInCartPreview
    }

    def propertyMissing(String propertyName){
        if("displayImage".equals(propertyName)){
            articleInCartPreview.displayImage = true
        }
        else if("displayPrice".equals(propertyName)){
            articleInCartPreview.displayPrice = true
        }
        else if("displayQuantity".equals(propertyName)){
                articleInCartPreview.displayQuantity = true
        }
        else if("allowRemoval".equals(propertyName)){
                articleInCartPreview.allowRemoval = true
        }else{
            println ("unexisting property ${propertyName}")
        }

    }
}
