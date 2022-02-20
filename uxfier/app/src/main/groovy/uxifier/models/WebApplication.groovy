package uxifier.models

import uxifier.vue.project.models.VueGeneratable
import uxifier.vue.project.models.VueJsAccordion
import uxifier.vue.project.models.VueJsAccordionGroup
import uxifier.vue.project.models.VueJsField
import uxifier.vue.project.models.VueJsForm
import uxifier.vue.project.models.VueJsSocialMediaGroup

class WebApplication {

    String name
    String title
    List<WebPage> pages = new ArrayList<>()

    NavigationMenu navigationMenu

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

    @Override
    def buildVue() {
        return null
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

    @Override
    def buildVue() {
        return null
    }
}

class HorizontalLayout implements Component{
        HorizontalLayout(List<Component> componentList){
            this.componentList = componentList
        }

    @Override
    def buildVue() {
        return null
    }
}

class VerticalLayout implements Component{

    @Override
    def buildVue() {
        return null
    }
}

class SocialMediaGroup implements Component{
    SocialMediaGroup(List<Component> componentList){
        this.componentList = componentList
    }
    String toString(){
        return "SocialMediaGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

class SocialMedia implements Component{
    SocialMediaType type
    String url

    @Override
    String toString() {
        return "SocialMedia {type = ${type},url = ${url} }"
    }

    @Override
    def buildVue() {
        return null
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

enum SocialMediaType {
    Facebook,
    LinkedIn,
    Instagram,
    Twitter,
    Pinterest
}

class Form implements Component{
    String name

    Form(){}

    Form(List<Component> componentList){
        this.componentList = componentList
    }

    Form(String name, List<Component> componentList){
        this.componentList = componentList
        this.name = name
    }

    @Override
    public String toString() {
        return "Form{" +
                "name='" + name + '\'' +
                ", components = ${componentList} " +
                '}';
    }

    @Override
    def buildVue() {
        var vue = new VueJsForm()
        vue.name = this.name
        for(Component c : this.componentList){
            if(c instanceof FieldGroup){
                for(Field f : (c.componentList as List<Field>)){
                    var tmpField = new VueJsField()
                    tmpField.setName(f.name)
                    tmpField.setType(f.type)
                    vue.fields.add(tmpField)
                }
            }
        }
        return vue
    }
}

class FieldGroup implements Component{
    FieldGroup(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "FieldGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Field implements Component{
    String type
    String name


    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    def buildVue() {
        return null
    }
}

class AccordionGroup implements Component{

    AccordionGroup(){
        this.componentList = new ArrayList<>()
    }

    AccordionGroup(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "AccordionGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        var tmp = new VueJsAccordionGroup();
        for(Accordion a : (this.componentList as List<Accordion>)){
            VueJsAccordion tmpAcc = new VueJsAccordion()
            tmpAcc.name = a.name
            for(Component c : a.componentList){
                var vue = c.buildVue()
                if(vue == null)
                    continue
                tmpAcc.components.add(vue as VueGeneratable)
            }
            tmp.accordions.add(tmpAcc)
        }
        return tmp
    }
}

class Accordion implements Component{
    String name

    Accordion(){
        this.componentList = new ArrayList<>()
    }


    @Override
    String toString(){
        return "Accordion {name = ${name}, components = ${componentList}}"
    }

    @Override
    def buildVue() {
        return null
    }
}


trait Component implements ApplicationModelVisitable {
    List<Component> componentList

    def addComponent(Component component) {
        this.componentList.add(component)
    }

    String toString() {
        return "Component {components = ${componentList} }"
    }

    abstract def buildVue()

    @Override
    def accept(ApplicationModelVisitor visitor) {
        visitor.visit(this)
    }
}

interface ApplicationModelVisitable {
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

    def visit(NavigationMenu navigationMenu)

    def visit(Menu menu)
    def visit(Form form)
    def visit(Field field)
    def visit(AccordionGroup accordionGroup)

    def visit(Action action)

    def visit(ActionMenuBar menuBar)

    def visit(CartAction action)
    def visit(Accordion accordion)

    def visit(CartPreview cartPreview)
}

trait LeafComponent{

}

trait CompositeComponent{

}

