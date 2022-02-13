package uxifier.vue.project.models

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.ToString

import java.nio.file.Files
import java.nio.file.Path

class VueComponent implements  VueGeneratable{
    VueTemplateElement template;
    ScriptElement script;
    String name
    List<VueGeneratable> content = new ArrayList<>();

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        this.content.add(vueGeneratable)
    }

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeTemplate() {
        var componentFilePath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), this.name+'.vue'))

        FileContext.writer =  Files.newBufferedWriter(componentFilePath)
        FileContext.writer.write("<template>")


        content.forEach(c -> c.writeTemplate())

        FileContext.writer.write("</template>")

        FileContext.writer.write("<script>")
        content.forEach(c -> c.importComponents())
        FileContext.writer.write("</script>")

        FileContext.writer.close()
        FileContext.writer = null


    }

    @Override
    def importComponents() {
        return null
    }

    @Override
    String toString() {
        return "VueComponent {name = ${name} } -> ${content}"
    }
}

class VueTemplateElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement{

}

trait HtmlElement {

}
trait LeafHtmlElement implements HtmlElement{

}
trait CompositeHtmlElement implements HtmlElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class HorizontalLayout implements HtmlElement{

}

class VerticalLayout implements HtmlElement{

}

class VueJsSocialMediaGroup implements VueGeneratable {
    List<VueJsSocialMedia> socialMedia = new ArrayList<>();

    @Override
    String toString() {
        return "VueJsSocialMediaGroup -> ${socialMedia}"
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return socialMedia.add(vueGeneratable)
    }

    @Override
    def registerDependencies() {
        return null;
    }

    @Override
    def writeTemplate() {

        FileContext.writer.write(
                """
<div><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ=" crossorigin="anonymous" />
                    """)

        socialMedia.forEach(s -> s.writeTemplate())
        FileContext.writer.write("</div>")

    }

    @Override
    def importComponents() {
        return null
    }
}

class VueJsCart implements VueGeneratable {

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeTemplate() {
        return """
        <div><div class="_2Cjr0"><main data-hook="CartAppDataHook.root" class="_1g1q8 _2tYB9"><main class="_3W3AQ"><section class="_35V3K"><div class="_3LoVd"><h1 class="_2NsyK" data-hook="HeadlineDataHook.root">Mon panier</h1></div><div data-hook="CartItemsDataHook.root"><ul><li data-hook="CartItemsDataHook.item"><div class="_1i4hd"><div class="JGj-R"><div class="_3ZPVi"><div class="_8-ZM0"><a href="https://www.wix.com/templatesfr/womens-accesso-fr/product-page/je-suis-un-article-2" data-hook="ItemLinkDataHooks.Anchor"><div class="_2mXn-" data-hook="product-thumbnail-wrapper"><div data-hook="containerDataHook" style="" class="T8ZEJ"><img class="" data-hook="product-thumbnail-media" src="https://static.wixstatic.com/media/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.jpg/v1/fill/w_100,h_133,al_c,q_85,usm_0.66_1.00_0.01/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.webp" srcset="https://static.wixstatic.com/media/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.jpg/v1/fill/w_100,h_133,al_c,q_85,usm_0.66_1.00_0.01/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.webp 1x, https://static.wixstatic.com/media/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.jpg/v1/fill/w_200,h_266,al_c,q_85,usm_0.66_1.00_0.01/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.webp 2x, https://static.wixstatic.com/media/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.jpg/v1/fill/w_300,h_399,al_c,q_85,usm_0.66_1.00_0.01/ea71bb_4f91ddb36f2346c9870d41f83ee4c93e~mv2_d_2487_3298_s_4_2.webp 3x" alt="Je suis un article" style="width: 100px; height: 133px;" aria-hidden="false"></div></div></a></div><div class="SiqV5"><div class="_1mqgN"><div data-hook="CartItemDataHook.name" class="_1dkgR">Je suis un article</div><div class="_2Ty7k"><span data-hook="CartItemDataHook.price">400,00 €</span></div></div><div data-content-hook="popover-content-CartItemDataHook.quantityErrorTooltip-undefined" class="Popover2202947389__root Popover1110205677__root Popover1110205677--wired Quantity4071813384__popover" id="popover_lnxeiver6" data-hook="CartItemDataHook.quantityErrorTooltip"><div class="Popover2202947389__popoverElement" data-hook="popover-element"><div class="Counter530147329__container Counter530147329__root Quantity4071813384__quantity Counter530147329__xSmall" dir="ltr" role="region" aria-label="Quantité" data-disabled="false" data-error="false" data-hook="CartItemDataHook.quantity"><button aria-label="Ajouter un élément" class="Button1672168483__root Counter530147329__btn Counter530147329__plus" data-hook="counter-plus-button" name="increment"><svg viewBox="0 0 24 24" fill="currentColor" width="24" height="24" class="Counter530147329__btnIcon"><path d="M13,5 L13,12 L20,12 L20,13 L13,13 L13,20 L12,20 L11.999,13 L5,13 L5,12 L12,12 L12,5 L13,5 Z" fill-rule="evenodd"></path></svg></button><div class="Counter530147329__inputWrapper"><div class="Input2252120060__root"><input aria-label="Choisir la quantité" aria-live="assertive" aria-describedby="[object Object]" type="number" min="1" max="99999" step="1" class="Input2252120060__nativeInput" value="1"></div></div><button aria-label="Supprimer un élément" class="Button1672168483__root Button1672168483--disabled Counter530147329__btn Counter530147329__minus" data-hook="counter-minus-button" name="decrement" disabled=""><svg viewBox="0 0 24 24" fill="currentColor" width="24" height="24" class="Counter530147329__btnIcon"><path d="M20,12 L20,13 L5,13 L5,12 L20,12 Z" fill-rule="evenodd"></path></svg></button></div></div></div></div></div><div class="cShBI"><div class="_32Pyj" data-hook="CartItemDataHook.totalPrice">400,00 €</div><button class="PKDLg" type="button" aria-label="supprimer Je suis un article du panier" data-hook="CartItemDataHook.remove"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor"><path fill-rule="evenodd" d="M11.077 0L12 .923 6.923 6 12 11.077l-.923.923L6 6.923.923 12 0 11.077 5.076 6 0 .923.923 0 6 5.077 11.077 0z"></path></svg></button></div></div></div></li></ul></div><footer class="mGNWY"><div><div class="_39cHM _1NraJ" data-hook="ApplyOffer.Root" role="textbox" tabindex="0"><button data-hook="Coupon.ShowButton" class="_30ckm" type="button"><svg viewBox="0 0 14 16" fill="currentColor" width="14" height="16"><g id="final-cart" stroke="none" fill="none" stroke-width="1" fill-rule="evenodd"><g id="general-layout" transform="translate(-515 -839)" fill="currentColor"><g id="coupon-icon" transform="rotate(30 -1300.653 1393.349)"><path d="M1,14.0046024 C0.999339408,13.9996515 9.00460243,14 9.00460243,14 C8.99965149,14.0006606 9,5.41421356 9,5.41421356 L5,1.41421356 L1,5.41421356 L1,14.0046024 Z M-2.72848411e-12,5 L5,-4.66116035e-12 L10,5 L10,14.0046024 C10,14.5543453 9.5443356,15 9.00460243,15 L0.995397568,15 C0.445654671,15 -2.72848411e-12,14.5443356 -2.72848411e-12,14.0046024 L-2.72848411e-12,5 Z" id="Rectangle-6" fill-rule="nonzero"></path><circle id="Oval-2" cx="5" cy="5" r="1"></circle></g></g></g></svg><span id="coupon-label" data-hook="Coupon.ShowButtonText">Saisissez un code promo</span></button><div data-hook="OfferForm.NoAnimationCollapse"></div></div></div><div><div class="_22Swy" data-hook="BuyerNoteDataHook.root"><button class="_2zjs7" type="button" data-hook="BuyerNoteDataHook.button" aria-expanded="false"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="13" viewBox="0 0 12 13" class="TUZIb"><g fill="none" fill-rule="evenodd" stroke="none" stroke-width="1"><g transform="translate(-515 -882)"><g transform="translate(515 882)"><path stroke="currentColor" d="M.5.5h7.778L11.5 3.737V12.5H.5V.5z"></path><path stroke="currentColor" d="M10.793 3.5H8.5V1.207L10.793 3.5z"></path><path fill="currentColor" d="M3 3H6V4H3z"></path><path fill="currentColor" d="M3 6H9V7H3z"></path><path fill="currentColor" d="M3 9H9V10H3z"></path></g></g></g></svg>Ajouter une remarque</button></div></div></footer><div class="_1wi76" data-hook="ScreenReaderMessage.Root" aria-live="polite" aria-relevant="additions text" aria-atomic="false"></div></section><aside class="_2Fd6W"><div class="_1q1PO"><h2 class="X7Zn6" data-hook="OrderSummary.headline">Résumé de la commande</h2><div data-hook="SubTotals.root" class="_3raX9"><dl><dt>Sous-total</dt><dd data-hook="SubTotals.subtotalText">400,00 €</dd></dl><div data-hook="TotalShipping.root"><dt><div class="_3zo99" data-hook="ShippingDestination.shippingDestination"><button data-hook="ShippingDestination.changeRegion" type="button" aria-haspopup="true">Frais d'expédition estimés</button></div></dt></div></div><dl class="_7EdR8"><dt><span data-hook="Total.title">Total</span></dt><dd data-hook="Total.formattedValue" role="status" aria-live="assertive" id="total-sum">400,00 €</dd></dl><dl><dt></dt></dl></div><div data-hook="CheckoutButtons.default" class="_34QVp"><button data-fullwidth="false" data-mobile="false" data-hook="CheckoutButtonDataHook.button" class="buttonnext3407251678__root Focusable1162858372__root Button1688183050__root Button1688183050--upgrade CheckoutButton2587600694__checkoutButton" type="button" tabindex="0" aria-disabled="false"><span class="buttonnext3407251678__content"><span class="CheckoutButton2587600694__checkoutButtonLabel">Paiement</span></span></button><div data-hook="cashier-express-buttons-container" class="_1-lx6"><div><div data-hook="ecw-root-wrapper"><div data-hook="express-checkout-widget-upc1anxqv"><section class="_2KlI0"><div data-hook="ecw-resource-loader"></div></section></div></div></div></div></div><div class="_1b8Ie"><svg width="11" height="14" viewBox="0 0 11 14" xmlns="http://www.w3.org/2000/svg" class="_2Muc8" data-hook="SecureCheckoutDataHook.lock"><g fill="currentColor" fill-rule="evenodd"><path d="M0 12.79c0 .558.445 1.01.996 1.01h9.008A1 1 0 0011 12.79V6.01c0-.558-.445-1.01-.996-1.01H.996A1 1 0 000 6.01v6.78z"></path><path d="M9.5 5v-.924C9.5 2.086 7.696.5 5.5.5c-2.196 0-4 1.586-4 3.576V5h1v-.924c0-1.407 1.33-2.576 3-2.576s3 1.17 3 2.576V5h1z" fill-rule="nonzero"></path></g></svg><span class="NtIgF" data-hook="SecureCheckoutDataHook.text">Paiement sécurisé</span></div></aside></main></main></div></div>
        """
    }

    @Override
    def importComponents() {
        return null
    }
}

class VueJsSocialMedia implements VueGeneratable {

    private final static Map<String, SocialMediaIconInfo> iconMaps = new HashMap<>();

    private final String name
    private final String link;

    VueJsSocialMedia(String name, String link) {
        this.name = name
        this.link = link
    }

    static {
         List<SocialMediaIconInfo>  iconInfos = FileContext.objectMapper.readValue(new String(VueJsSocialMedia.getResourceAsStream("/social-media.json").readAllBytes()),new TypeReference<List<SocialMediaIconInfo>>() {
         } )

        iconInfos.forEach( info -> iconMaps.put(info.name, info))

    }

    @Override
    String toString() {
        return "VueJsSocialMedia {name = ${name} , link = ${link} }"
    }

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeTemplate() {
        FileContext.writer.write("""<a href="${this.link}"> <em style="color:${iconMaps.get(this.name).color};" class="${iconMaps.get(this.name).icon}"</em></a>""")
    }

    @Override
    def importComponents() {
        return null
    }
}

trait VueGeneratable{

    //NOTE : deux contextes pour un composant un où on génère son propre fichier et un où il est utilisé dans un autre fichier


    abstract def registerDependencies()
    abstract def writeTemplate()
    abstract def importComponents()
    def toCode(){
        this.registerDependencies()
        this.writeTemplate()
        this.importComponents()
    }

    def addContent(VueGeneratable vueGeneratable){

    }
}

@ToString
class SocialMediaIconInfo{
    String network
    String name
    String icon
    String color
}
