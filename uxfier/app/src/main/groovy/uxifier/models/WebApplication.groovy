package uxifier.models

class WebApplication {

    String name

    List<WebPage> pages = new ArrayList<>()

    @Override
    String toString() {
        return "WebApplication { name = ${name}, pages = ${pages} }"
    }


    def addWebPage(WebPage page){
        this.pages.add(page)
    }
}

class WebPage implements Component{
    String name
    String title

    @Override
    String toString() {
        return "WebPage {title = ${title} , name = ${name}, components = ${componentList} }"
    }
}

class Header implements Component{
    Header(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "Header {components = ${componentList} }"
    }
}

class HorizontalLayout implements Component{
        HorizontalLayout(List<Component> componentList){
            this.componentList = componentList
        }
}

class VerticalLayout implements Component{
    VerticalLayout(List<Component> componentList){
        this.componentList = componentList
    }
}

class SocialMediaGroup implements Component{
    SocialMediaGroup(List<Component> componentList){
        this.componentList = componentList
    }
    String toString(){
        return "SocialMediaGroup {components = ${componentList} }"
    }
}

class SocialMedia implements Component{
    SocialMediaType type
    String url

    @Override
    String toString() {
        return "SocialMedia {type = ${type},url = ${url} }"
    }
}

class Poster implements Component{

}

class MiniDescription implements Component{

}

class QuantityInCart implements Component{
    QuantityInCartEditionMode quantityInCartEditionMode;
    def setQuantityInCartEditionMode(QuantityInCartEditionMode quantityInCartEditionMode){
        this.quantityInCartEditionMode = quantityInCartEditionMode
    }
}

class ProductInCart implements Component{
    List<Component> componentList = new ArrayList<>();
    var deletable = false

    def enableDeleteable(){
        deletable = true
    }

    def addTotalComponent(){
        
    }

    @Override
    String toString() {
        return "Product{" +
                "Poster" +
                "MiniDescription" +
                "Quantity" +
                "Total" +
                "}"
    }
}

trait OneLineAbleLabelledComponent implements Component{
    String label
    String componentName
    @Override
    String toString() {
        return "${componentName} label ${label}"
    }
}


class PromoCode implements OneLineAbleLabelledComponent{
    PromoCode(){
        componentName = "PromoCode"
    }
}

class Remark implements OneLineAbleLabelledComponent{
    Remark(){
        componentName = "Remark"
    }
}
class SubTotal implements OneLineAbleLabelledComponent{
    SubTotal(){
        componentName = "SubTotal"
    }
}
class Total implements OneLineAbleLabelledComponent{
    Total(){
        componentName = "Total"
    }
}

class DeliveryInCart implements Component {
    String label
    Integer defaultValue
    @Override
    String toString() {
        return  "                        Delivery{\n" +
                "                            label ${label}\n" +
                "                            default ${defaultValue}\n" +
                "                        }\n"
    }

}

class Summary implements Component {
    DeliveryInCart delivery
    String label
    SubTotal subTotal
    Total total
    @Override
    String toString() {
        return "Summary{\n" +
                "                        label ${label}\n" +
                "\n" +
                "                        ${subTotal}\n" +
                "\n" +
                "${delivery}" +
                "\n" +
                "                        ${total}\n" +
                "                    }"
    }
}

class Cart implements Component{

    String title
    ProductInCart productInCart
    PromoCode promoCode
    Remark remark
    Summary summary

    @Override
    String toString() {
        return "Cart {\n" +
                "title = ${title},\n" +
                "Products = { \n" +
                "${productInCart}\n" +
                "  }\n" +
                "${promoCode}\n" +
                "${remark}\n" +
                "${summary}\n" +
                " }"
    }
}

enum DeletableAnswer{
    yes,
    no
}

enum QuantityInCartEditionMode{
    Default
}

enum SocialMediaType{
    Facebook,
    LinkedIn,
    Instagram,
    Twitter,
    Pinterest
}


trait Component implements ApplicationModelVisitable{
    List<Component> componentList

    def addComponent(Component component){
        this.componentList.add(component)
    }

    String toString(){
        return "Component {components = ${componentList} }"
    }

    @Override
    def accept(ApplicationModelVisitor visitor) {
        visitor.visit(this)
    }
}

interface ApplicationModelVisitable{
    def accept(ApplicationModelVisitor visitor)
}

interface ApplicationModelVisitor{
    def visit(SocialMedia media)
    def visit(HorizontalLayout layout)
    def visit(Component component)
    def visit(SocialMediaGroup socialMediaGroup)
    def visit(Cart cart)
    def visit(Header header)
    def visit(WebApplication application)
    def visit(WebPage webPage)
}

trait LeafComponent{

}

trait CompositeComponent{

}


