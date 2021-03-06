package uxifier.vue.project.models

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.ToString
import uxifier.models.*

import java.nio.file.Files
import java.nio.file.Path

class VueComponent implements VueGeneratable {
    VueTemplateElement template;
    ScriptElement script;
    String name
    List<VueGeneratable> content = new ArrayList<>();

    @Override
    def writeStyle() {
        FileContext.writer.write("<style>")
        content.forEach(c->c.insertSelfInStyle())
        FileContext.writer.write("</style>")

        FileContext.writer.close()
        FileContext.writer = null
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        this.content.add(vueGeneratable)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeTemplate() {
        println("generating vuecomponent with name  ${name} ")
        var componentFilePath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), this.name + '.vue'))

        FileContext.writer = Files.newBufferedWriter(componentFilePath)
        FileContext.writer.write("<template>")
        println("wrote <template> ${name}")
        content.forEach(c -> c.insertInTemplate())
        println("wrote inserted content ${name}")
        FileContext.writer.write("</template>")

    }

    @Override
    def insertSelfInImports() {
        FileContext.writer.write("import ${this.name} from './components/${this.name}.vue'\n")
    }

    @Override
    def registerSelfInComponents() {
        FileContext.writer.write("${this.name},")
    }

    @Override
    def writeScript() {
        println 'importing libraries for all components...'
        FileContext.writer.write("<script>")
        content.forEach(c -> c.insertSelfInImports())

        FileContext.writer.write("""export default {
            name: '${name}',""")

        FileContext.writer.write("""components :{""")

        content.forEach(c -> c.registerSelfInComponents())
        FileContext.writer.write("}, data() {\nreturn{")
        content.forEach(c -> c.insertInData())

        FileContext.writer.write("}}}\n</script>")
    }


    @Override
    def insertInTemplate() {
        FileContext.writer.write("<${this.name}/>")
    }

    @Override
    String toString() {
        return "VueCoponent {name = ${name} } -> ${content}"
    }
}

class VueTemplateElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement {

}

trait HtmlElement {
    /**
     * used for nested components
     * such as tabs or accordions
     * */
    def openTagInTemplate(){

    }

    def closeTagInTemplate(){

    }
}

trait LeafHtmlElement implements HtmlElement {

}

trait CompositeHtmlElement implements HtmlElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class VueJSHorizontalLayout implements VueGeneratable {

    List<VueGeneratable> components = new ArrayList<>()

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
        FileContext.writer.write("""<vaadin-horizontal-layout>""")
        for(VueGeneratable v : components){
            FileContext.writer.write("""<vaadin-vertical-layout>""")

            v.insertInTemplate()
            FileContext.writer.write("""</vaadin-vertical-layout>""")

        }
        FileContext.writer.write("""</vaadin-horizontal-layout>""")
    }

}

class VerticalLayout implements HtmlElement {

}

class VueJsSocialMediaGroup implements VueGeneratable {
    List<VueGeneratable> socialMedia = new ArrayList<>();

    @Override
    String toString() {
        return "VueJsSocialMediaGroup -> ${socialMedia}"
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return socialMedia.add(vueGeneratable)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null;
    }

    @Override
    def writeTemplate() {

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
        FileContext.writer.write(
                """
<div><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ=" crossorigin="anonymous" />
                    """)
        socialMedia.forEach(s -> s.insertInTemplate())
        FileContext.writer.write("</div>")

    }

}

class VueJsCart implements VueGeneratable {
    Cart cart;

    List<VueGeneratable> cartContent = new ArrayList<>();

    public VueJsCart(Cart cart){
        this.cart = cart
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return cartContent.add(vueGeneratable)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def insertSelfInImports() {

    }



    @Override
    def writeScript() {
        return null
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }


    def insertInTemplate() {
        VueJsProductInCart productInCart = null
        VueJsPromoCode promoCode = null
        VueJsRemark remark = null
        VueJsSummary summary = null

        for (int i=0;i<cartContent.size();i++) {
            var cartContentI = cartContent.get(i)
            if (cartContentI instanceof VueJsProductInCart)
                productInCart = (VueJsProductInCart) cartContentI
            if (cartContentI instanceof VueJsPromoCode)
                promoCode = (VueJsPromoCode) cartContentI
            if (cartContentI instanceof VueJsRemark)
                remark = (VueJsRemark) cartContentI
            if (cartContentI instanceof VueJsSummary)
                summary = (VueJsSummary) cartContentI
        }

        FileContext.writer.write("""
 <vaadin-horizontal-layout style="width: 100%;">

    <vaadin-vertical-layout style="width: 100%;">
      <vaadin-vertical-layout style="width: 100%;">
        <vaadin-label>"""+(cart.getTitle()?cart.getTitle():"")+"""</vaadin-label>
    
        <hr style="width: 100%;"/>
            """)
            if(productInCart)
                    productInCart.insertInTemplate()
        FileContext.writer.write("""
      </vaadin-vertical-layout>
    
        <vaadin-vertical-layout>
    """)

        if (promoCode)
            promoCode.insertInTemplate()

        if(remark)
            remark.insertInTemplate()

        FileContext.writer.write("""
      </vaadin-vertical-layout>

      """)
        if (summary)
            summary.insertInTemplate(SMALL_SCREEN_WIDTH)

        FileContext.writer.write("""
    </vaadin-vertical-layout>

    """)
        if(summary)
            summary.insertInTemplate()
    FileContext.writer.write("""
  </vaadin-horizontal-layout>
        """)
    }

    @Override
    def insertSelfInStyle() {
        FileContext.writer.write("""
            .smallScreen{
              display:none;
            }
            
            @media screen and (max-width: 992px) {
              .largeScreen{
                display:none;
              }
              .smallScreen{
                display: flex;
              }
            }
""")
    }


}

class VueJsProductInCart implements VueGeneratable,VueResponsive{
    ProductInCart productInCart;

    List<VueGeneratable> productInCartContent = new ArrayList<>();

    public VueJsProductInCart(ProductInCart productInCart){
        this.productInCart = productInCart
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return productInCartContent.add(vueGeneratable)
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }


    def insertInTemplate() {
        VueJsPoster poster = null
        VueJsQuantityOfProductInCart productQuantityInCart = null
        for (int i=0;i<productInCartContent.size();i++) {
            var cartContentI = productInCartContent.get(i)
            if (cartContentI instanceof VueJsPoster)
                poster = (VueJsPoster) cartContentI
            if (cartContentI instanceof VueJsQuantityOfProductInCart)
                productQuantityInCart = (VueJsQuantityOfProductInCart) cartContentI
        }
        FileContext.writer.write("""
        <vaadin-horizontal-layout style="width: 100%;">
        """)
            if(poster) {
                poster.insertInTemplate()
                poster.insertInTemplate(SMALL_SCREEN_WIDTH)
            }

    FileContext.writer.write("""
              <vaadin-vertical-layout
                  style="justify-content: flex-start; padding-left: 5px;">
                <layout-item><span>Je suis un article</span></layout-item>
                <layout-item>
                  <vaadin-number-field edit="false" value="400" readonly>
                    <div slot="suffix">???</div>
                  </vaadin-number-field>
                </layout-item> """)
                if(productQuantityInCart)
                    productQuantityInCart.insertInTemplate()
    FileContext.writer.write( """
         </vaadin-vertical-layout>
              """)
            if(productInCart.getTotalComponent()||productInCart.getDeletable()) {

                FileContext.writer.write("""
    
            <vaadin-horizontal-layout class="largeScreen" style="justify-content: space-evenly; flex-grow: 2;">
                                        """)

                if(productQuantityInCart)
                    productQuantityInCart.insertInTemplate(SMALL_SCREEN_WIDTH)

                if (productInCart.getTotalComponent())
                    FileContext.writer.write("""
                        <layout-item style="justify-content: space-around;" >
                          <vaadin-number-field value="800"  readonly >
                            <div slot="suffix">???</div>
                          </vaadin-number-field>
                        </layout-item>""")

                if (productInCart.getDeletable())
                    FileContext.writer.write("""
                        <vaadin-button aria-label="Close" theme="icon">
                          <vaadin-icon icon="vaadin:close-small"></vaadin-icon>
                        </vaadin-button>""")

                FileContext.writer.write("""
            </vaadin-horizontal-layout>
                    """)
            }
            if(productInCart.getTotalComponent()||productInCart.getDeletable()){
                FileContext.writer.write("""
                <vaadin-vertical-layout class="smallScreen" style="justify-content: space-between; flex-grow: 2;">""")

                        if(productInCart.getDeletable()){
                            FileContext.writer.write("""
                                        <layout-item style="padding-left: 40%">
                                          <vaadin-button aria-label="Close" theme="icon">
                                            <vaadin-icon icon="vaadin:close-small"></vaadin-icon>
                                          </vaadin-button>
                                        </layout-item>""")
                        }
                        if (productInCart.getTotalComponent()){
                            FileContext.writer.write("""
                                        <layout-item style="padding-left: 2%">
                                        <vaadin-label style="padding-left: 2%">"""+productInCart.getTotalLabel()+"""</vaadin-label>
                                          <vaadin-number-field value="800"  readonly >
                                            <div slot="suffix">???</div>
                                          </vaadin-number-field>
                                        </layout-item>""")
                        }
                FileContext.writer.write("""
                </vaadin-vertical-layout>
                        """)
        }

        FileContext.writer.write("""
        </vaadin-horizontal-layout>
                """)
    }
/**
 * insert self in a parent template
 * @return
 */

    @Override
    def insertInTemplate(Integer integer) {
        return null
    }
}

class VueJsPromoCode implements VueGeneratable {

    PromoCode promoCode;

    List<VueGeneratable> promoCodeContent = new ArrayList<>();

    public VueJsPromoCode(PromoCode promoCode){
        this.promoCode = promoCode
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""
            <vaadin-horizontal-layout>
    
              <vaadin-text-field label=\""""+(promoCode.getLabel()? promoCode.getLabel():"Saisissez un code promo")+"""\">
                <vaadin-icon icon="vaadin:ticket"></vaadin-icon>
              </vaadin-text-field>
    
              <vaadin-vertical-layout style="justify-content: end;">
                <vaadin-button >Appliquer</vaadin-button>
              </vaadin-vertical-layout>
    
            </vaadin-horizontal-layout>
        """)
    }
}

class VueJsRemark implements VueGeneratable {
    Remark remark;

    List<VueGeneratable> remarkContent = new ArrayList<>();

    public VueJsRemark(Remark remark){
        this.remark = remark
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""
            <vaadin-text-area
                .maxlength="1233"
                .value="toto"
                label=\""""+(remark.getLabel()? remark.getLabel():"Ajouter une remarque")+"""\"
                width="100">
              <vaadin-icon icon="vaadin:file-text-o"></vaadin-icon>
            </vaadin-text-area>
        """)
    }
}

class VueJsSummary implements VueGeneratable,VueResponsive {
    Summary summary;

    List<VueGeneratable> summaryContent = new ArrayList<>();

    public VueJsSummary(Summary summary){
        this.summary = summary
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return summaryContent.add(vueGeneratable)
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    def insertInTemplate() {
        VueJsDeliveryInCart deliveryInCart = null;
        VueJsSubTotal subTotal = null;
        VueJsTotal total = null;

        for (int i=0; i<summaryContent.size(); i++) {
            var contentI = summaryContent.get(i)
            if(contentI instanceof VueJsDeliveryInCart)
                deliveryInCart = (VueJsDeliveryInCart)contentI
            if(contentI instanceof VueJsSubTotal)
                subTotal = (VueJsSubTotal)contentI
            if(contentI instanceof VueJsTotal)
                total = (VueJsTotal)contentI
        }

        FileContext.writer.write("""
        <vaadin-vertical-layout style="width: 40%;padding-left: 40px;" class="largeScreen">
        
              <vaadin-label>"""+(summary.getLabel()?summary.getLabel():"R??sum?? de la commande")+"""</vaadin-label>
        
              """)

        if(subTotal||deliveryInCart){
            FileContext.writer.write("""
                    <hr style="width: 100%;"/>
                    """)
                    if (subTotal)
                        subTotal.insertInTemplate()


                    if (deliveryInCart)
                        deliveryInCart.insertInTemplate()
                }

        FileContext.writer.write("""
              <hr style="width: 100%;"/>
        
              """)

                if(total)
                    total.insertInTemplate()

                FileContext.writer.write("""
        
              <vaadin-button class="btn btn-primary" style="justify-content: center; width: 100%;" theme="primary">Paiement</vaadin-button>
        
            </vaadin-vertical-layout>
        """)
    }
/**
 * insert self in a parent template
 * @return
 */

    @Override
    def insertInTemplate(Integer integer) {
        VueJsDeliveryInCart deliveryInCart = null;
        VueJsSubTotal subTotal = null;
        VueJsTotal total = null;

        for (int i=0; i<summaryContent.size(); i++) {
            var contentI = summaryContent.get(i)
            if(contentI instanceof VueJsDeliveryInCart)
                deliveryInCart = (VueJsDeliveryInCart)contentI
            if(contentI instanceof VueJsSubTotal)
                subTotal = (VueJsSubTotal)contentI
            if(contentI instanceof VueJsTotal)
                total = (VueJsTotal)contentI
        }

        if(integer == SMALL_SCREEN_WIDTH){
            FileContext.writer.write("""
            <vaadin-vertical-layout style="width: 100%; padding-top: 20px;" class="smallScreen">
            
                    <vaadin-label>"""+(summary.getLabel()?summary.getLabel():"R??sum?? de la commande")+"""</vaadin-label>
            
            
            
                    """)

                    if(subTotal||deliveryInCart){
                        FileContext.writer.write("""
                    <hr style="width: 100%;"/>
                    """)
                        if (subTotal)
                            subTotal.insertInTemplate()

                        if (deliveryInCart)
                            deliveryInCart.insertInTemplate()
                    }

                    FileContext.writer.write("""
            
                    <hr style="width: 100%;"/>""")


            if(total)
                total.insertInTemplate()

            FileContext.writer.write("""
                    <vaadin-button class="btn btn-primary" style="justify-content: center; width: 100%;" theme="secondary">Paiement</vaadin-button>
            
                  </vaadin-vertical-layout>
        """)
        }
    }
}

class VueJsPoster implements VueGeneratable,VueResponsive {
    Poster poster;

    public VueJsPoster(Poster poster){
        this.poster = poster
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    def insertInTemplate() {
        var img = String.format("<img class=\"largeScreen\" src=\"https://picsum.photos/200\" width=\"%s\" height=\"%s\">",poster.getLargeWidth(),poster.getLargeHeight())
        FileContext.writer.write(img)
    }
/**
 * insert self in a parent template
 * @return
 */

    @Override
    def insertInTemplate(Integer integer) {
        var img = String.format("<img class=\"smallScreen\" src=\"https://picsum.photos/200\" width=\"%s\" height=\"%s\">",poster.getSmallWidth(),poster.getSmallHeight())

        if(integer == SMALL_SCREEN_WIDTH){
            FileContext.writer.write(img)
        }
    }
}

class VueJsMiniDescription implements VueGeneratable {
    MiniDescription miniDescription;

    public VueJsMiniDescription(MiniDescription miniDescription){
        this.miniDescription = miniDescription
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""
VueJsMiniDescription
        """)
    }
}

class VueJsQuantityOfProductInCart implements VueGeneratable,VueResponsive {
    QuantityInCart quantityInCart;

    VueJsQuantityOfProductInCart(QuantityInCart quantityInCart){
        this.quantityInCart = quantityInCart
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""
                <layout-item>
                  <vaadin-integer-field has-controls class="smallScreen" max="100000" min="0" value="2" """+(quantityInCart.getEditable()?"":"readonly")+"""></vaadin-integer-field>
                </layout-item>
        """)
    }
/**
 * insert self in a parent template
 * @return
 */

    @Override
    def insertInTemplate(Integer integer) {
        if(integer==SMALL_SCREEN_WIDTH){
            FileContext.writer.write("""
                <layout-item>
                  <vaadin-integer-field has-controls max="100000" min="0" value="2" """+(quantityInCart.getEditable()?"":"readonly")+"""></vaadin-integer-field>
                </layout-item>
        """)
        }
    }
}

class VueJsTotal implements VueGeneratable {
    Total total;

    public VueJsTotal(Total total){
        this.total = total
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""
                          <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">
                    
                            <vaadin-label>"""+(this.total.getLabel()?this.total.getLabel():"Total")+"""</vaadin-label>
                    
                            <layout-item style="padding-left: 2%">
                              <vaadin-number-field value="800"  readonly >
                                <div slot="suffix">???</div>
                              </vaadin-number-field>
                            </layout-item>
                          </vaadin-horizontal-layout>
        """)
    }
}

class VueJsSubTotal implements VueGeneratable {
    SubTotal subTotal;

    public VueJsSubTotal(SubTotal subTotal){
        this.subTotal = subTotal
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""

              <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">
        
                <vaadin-label>"""+(subTotal.getLabel()?subTotal.getLabel():"Sous total")+"""</vaadin-label>
        
                <layout-item style="padding-left: 2%">
                  <vaadin-number-field value="800"  readonly >
                    <div slot="suffix">???</div>
                  </vaadin-number-field>
                </layout-item>
              </vaadin-horizontal-layout>"""
        )
    }

}
class VueJsDeliveryInCart implements VueGeneratable {
    DeliveryInCart deliveryInCart;

    public VueJsDeliveryInCart(DeliveryInCart deliveryInCart){
        this.deliveryInCart = deliveryInCart
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    def insertInTemplate() {
        FileContext.writer.write("""

              <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">
        
                <vaadin-label>"""+(deliveryInCart.getLabel()?deliveryInCart.getLabel():"Frais d'exp??dition estim??s")+"""</vaadin-label>
        
                <layout-item style="padding-left: 2%">
                  <vaadin-number-field value=\""""+(deliveryInCart.getDefaultValue()?deliveryInCart.getDefaultValue():0)+"""\"  readonly >
                    <div slot="suffix">???</div>
                  </vaadin-number-field>
                </layout-item>
              </vaadin-horizontal-layout>""")
    }
}


class VueJsPriceFilter implements VueGeneratable {

    private final PriceType type

    VueJsPriceFilter(PriceType type) {
        this.type = type
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
    def insertInData() {}

    @Override
    def insertInTemplate() {
        if(this.type === PriceType.Bar){
        FileContext.writer.write("""
  <div class="card-body">
    <form id="price-range-form">
      <label for="min-price" class="form-label">Min price: </label>
      <span id="min-price-txt">\$0</span>
      <input type="range" class="form-range" min="0" max="99" id="min-price" step="1" value="0">
      <label for="max-price" class="form-label">Max price: </label>
      <span id="max-price-txt">\$100</span>
      <input type="range" class="form-range" min="1" max="100" id="max-price" step="1" value="100">
    </form>
  </div>
          """)
        }
        else {
            // TODO
        }

    }
}


class VueJsFilter implements VueGeneratable {

    VueJsPriceFilter priceFilter
    VueJsGenericFilters genericFilters


    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertInData() {
        priceFilter.insertInData()
        genericFilters.insertInData()
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        priceFilter.insertInTemplate()
        genericFilters.insertInTemplate()
    }
}

class VueJsRating implements VueGeneratable {

    private final RatingType type

    VueJsRating(RatingType type) {
        this.type = type
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
        return null
    }
}


class VueJsGenericFilters implements VueGeneratable {

    List<VueGeneratable> genericFilters = new ArrayList<>()

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        genericFilters.add(vueGeneratable)
    }


    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }


    @Override
    def insertInData() {
        for (VueGeneratable v : genericFilters) {
            v.insertInData()
        }
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        return "this is generic filters"
    }
}


class VueJsProduct implements VueGeneratable {

    final Product product

    VueJsProduct(Product product) {
        this.product = product
    }


    @Override
    def insertInData() {
        FileContext.writer.write("""
            products: [
              { name: 'Foo' },
              { name: 'Bar' },
              { name: 'Dir' }
            ]
        """)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeTemplate() {
        println(this.product)

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
        FileContext.writer.write("""
                <p>{{product.name}}</p>""")
        if (this.product.rating.ratingType == RatingType.Stars) {
            FileContext.writer.write("""
                <star-rating />
        """)

        } else {
            // TODO
        }

    }


    @Override
    String toString() {
        return "VueJsProduct {product = ${product}}"
    }

}


class VueJsGenericFilter implements VueGeneratable {

    private final String targetAtributName
    private final String targetAtributType

    VueJsGenericFilter(String targetAtributType, String targetAtributName) {
        this.targetAtributType = targetAtributType
        this.targetAtributName = targetAtributName
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }


    @Override
    def insertInData() {

    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        return null
    }
}


class VueJsCatalog implements VueGeneratable {


    VueJsProduct product
    VueJsFilter filtre


    VueJsCatalog() {
    }

    @Override
    String toString() {
        return "VueJsCatalog {catalog =  }}"
    }


    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def insertInData() {
        println("generate product data")
        product.insertInData()
        filtre.insertInData()
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def registerSelfInComponents() {
        FileContext.writer.write("""StarRating,""")
    }

    @Override
    def writeTemplate() {
        return null

    }


    @Override
    def insertSelfInStyle() {

        if(product.product.printingType ==  PrintingType.ROW ){

            FileContext.writer.write(""".layout{display:flex; flex-direction: row;}""")
        }

        else {
            FileContext.writer.write(""".layout{display:flex; flex-direction: column;}""")

        }

    }

    @Override
    def insertSelfInImports() {
        FileContext.writer.write("""import StarRating from 'vue-star-rating';
""")
    }

    @Override
    def insertInTemplate() {
        filtre.insertInTemplate()
        FileContext.writer.write("""
            
            <div class="layout">
            <div v-for="product in products" :key="product.name">  """)

        product.insertInTemplate()

        FileContext.writer.write("""
            </div>
            </div>
        """)

    }

}


class VueJsSocialMedia implements VueGeneratable {

    private final static Map<String, SocialMediaIconInfo> iconMaps = new HashMap<>();

    private final String name
    private final String link

    VueJsSocialMedia(String name, String link) {
        this.name = name
        this.link = link
    }

    static {
        List<SocialMediaIconInfo> iconInfos = FileContext.objectMapper.readValue(new String(VueJsSocialMedia.getResourceAsStream("/social-media.json").readAllBytes()), new TypeReference<List<SocialMediaIconInfo>>() {
        })

        iconInfos.forEach(info -> iconMaps.put(info.name, info))

    }

    @Override
    String toString() {
        return "VueJsSocialMedia {name = ${name} , link = ${link} }"
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def insertInTemplate() {
        println("in vuejs-socialmedia ${this.name}")
        FileContext.writer.write("""\n<a href="${this.link}"> <em style="color:${iconMaps.get(this.name).color};" class="${iconMaps.get(this.name).icon}"> </em></a>""")
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }
}

class VueJsForm implements VueGeneratable {

    String name
    List<VueGeneratable> fields = new ArrayList<>();

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
        println 'creating form : ' + name + 'with fields size' + fields.size()
        FileContext.writer.write("""<div style="width: 50%"><vaadin-form-layout name="${name}">""")
        if(name != null && name != "null"){
            FileContext.writer.write("""<h3 style="font-family: 'Open Sans'; padding-left:10%">${name}</h3>""");
        }
        fields.forEach(s -> s.insertInTemplate())
        FileContext.writer.write("""</vaadin-form-layout></div>""")
    }

    @Override
    public String toString() {
        return "VueJsForm{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}

class VueJsField implements VueGeneratable {

    String name
    String type

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
        var required = name.substring(0,1).equals("*")
        if(required){
            name = name.substring(1, name.size())
        }
        if(type.equals("button")){
            FileContext.writer.write("""<vaadin-${type}  style="width: 45%; margin-left:25%;">${name}</vaadin-${type}>""")
            return
        }
        if(type.equals("email-field")){
            FileContext.writer.write("""<vaadin-${type}  style="width: 80%; padding-left:10%;" label="${name}" error-message="Please enter a valid email address"/>""")
            return
        }
        if(required){
            FileContext.writer.write("""<vaadin-${type} required=${required} style="width: 80%; padding-left:10%;" label="${name}" error-message="${name} is required."/>""")
            return
        }
        FileContext.writer.write("""<vaadin-${type} style="width: 80%; padding-left:10%;" label="${name}"/>""")
    }


    @Override
    public String toString() {
        return "VueJsField{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

class VueJsRadioGroup implements VueGeneratable{

    List<VueGeneratable> fields = new ArrayList<>();
    String name;
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
        FileContext.writer.write("""<vaadin-radio-group style="padding-left:10%;" label="${name}" theme="vertical">""")

        fields.forEach(s -> s.insertInTemplate())

        FileContext.writer.write("""</vaadin-radio-group>""")
    }


    @Override
    public String toString() {
        return "VueJsRadioGroup{" +
                "fields=" + fields +
                '}';
    }
}

class VueJsAccordionGroup implements VueGeneratable {

    List<VueGeneratable> accordions = new ArrayList<>();

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
        FileContext.writer.write("""<vaadin-accordion style="margin-left: 15%; margin-right: 2%; margin-top: 2%">""")
        for (VueGeneratable v : accordions) {
            v.insertInTemplate()
        }
        FileContext.writer.write("""</vaadin-accordion>""")
    }

    @Override
    public String toString() {
        return "VueJsAccordionGroup{" +
                "accordions=" + accordions +
                '}';
    }
}

class VueJsAccordion implements VueGeneratable {
    String name
    List<VueGeneratable> components = new ArrayList<>()

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
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
        FileContext.writer.write("""<vaadin-accordion-panel>
        <vaadin-vertical-layout>""")
        for (VueGeneratable v : components) {
            v.insertInTemplate()
        }
        FileContext.writer.write("""</vaadin-vertical-layout>
        </vaadin-accordion-panel>""")
    }

    @Override
    public String toString() {
        return "VueJsAccordion{" +
                "name='" + name + '\'' +
                ", components=" + components +
                '}';
    }
}

trait VueResponsive{
    /**
     * insert self in a parent template
     * @return
     */
    abstract def insertInTemplate(Integer integer)
}

trait VueGeneratable {
    int SMALL_SCREEN_WIDTH = 992
    //NOTE : deux contextes pour un composant un o?? on g??n??re son propre fichier et un o?? il est utilis?? dans un autre fichier


    def registerDependencies(PackageJson packageJson) {

    }

    /**
     * Write own template
     */
    def writeTemplate() {

    }

    def insertInData() {

    }

    abstract def writeScript()

    def registerSelfInComponents() {

    }

    def writeStyle() {

    }

    def insertSelfInStyle() {

    }

    abstract def insertSelfInImports()

    /**
     * insert self in a parent template
     * @return
     */
    abstract def insertInTemplate()


    /**
     * used for nested components
     * such as tabs or accordions
     * */
    def openTagInTemplate() {

    }

    def closeTagInTemplate(){

    }

    def toCode(VueProject project) {
        this.registerDependencies(project.packageJson)
        this.writeTemplate()
        this.writeScript()
        this.writeStyle()
        if (FileContext.writer != null && FileContext.writer) {
            FileContext.writer.close()
        }

    }

    def addContent(VueGeneratable vueGeneratable) {

    }
}

@ToString
class SocialMediaIconInfo {
    String network
    String name
    String icon
    String color
}

