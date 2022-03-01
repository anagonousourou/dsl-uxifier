package uxifier.models

import uxifier.vue.project.models.*


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

class Catalog implements Component{
    Product product
    Filter filter

    Catalog(List<Component> componentList){
        for(Component c : componentList){
            if (c instanceof Product){
                product = c
            }
            if(c instanceof  Filter){
                filter = c
            }
        }
    }

    @Override
    String toString(){
        return "product : " + product + "\nfilter : " + filter
    }

    @Override
    def buildVue() {
        return null
    }
}


class Product implements Component {

    Rating rating

    PrintingType printingType

    @Override
    String toString(){

        return "rating : "+ rating+"\n"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Rating implements Component{
    RatingType ratingType

    @Override
    String toString(){
        return "rating type : " + ratingType
    }

    @Override
    def buildVue() {
        return null
    }
}

enum RatingType {
    Stars,
    Bar,
    Mark

}


class Filter implements Component{

    PriceFilter priceFilter

    GenericFilters genericFilters

    Filter(List<Component> compoents){

        for(Component c: compoents){
            if(c instanceof PriceFilter){
                this.priceFilter = (PriceFilter) c
            }

            if(c instanceof GenericFilters){
                this.genericFilters = (GenericFilters) c
            }

        }

    }

    @Override
    def buildVue() {
        return null
    }
}

class GenericFilter implements Component{

    String targetAtributName
    String targetAtributType

    @Override
    String toString(){
        return "targetAtributName : " + targetAtributName + "\ntargetAtributType" + targetAtributType
    }

    @Override
    def buildVue() {
        return null
    }
}

class PriceFilter implements Component{

    PriceType priceType

    @Override
    String toString(){
        return "type : " + priceType
    }

    @Override
    def buildVue() {
        return null
    }
}

enum PriceType {

    Range,
    Bar,
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

    HorizontalLayout(){
        this.componentList = new ArrayList<>()
    }

    HorizontalLayout(List<Component> componentList){
        this.componentList = componentList
    }

    @Override
    public String toString() {
        return "HorizontalLayout{" +
                "components = ${componentList} " +
                '}';
    }

    @Override
    def buildVue() {
        var tmp = new VueJSHorizontalLayout()
        for(Component c : componentList){
            var vue = c.buildVue()
            if(vue == null)
                continue
            tmp.components.add(vue as VueGeneratable)
        }
        return tmp
    }
}

class VerticalLayout implements Component{

    @Override
    def buildVue() {
        return null
    }
}


class GenericFilters implements Component {
    GenericFilters(List<Component> componentList){
        this.componentList = componentList
    }

    @Override
    String toString() {
        return "GenericFilters {components = ${componentList} }"
    }

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
    int largeWidth = 233
    int smallWidth = 133
    int largeHeight = 200
    int smallHeight = 100
    @Override
    def buildVue() {
        return null
    }
}

class MiniDescription implements Component{

    @Override
    def buildVue() {
        return null
    }
}

class QuantityInCart implements Component{
    EditableAnswer editableAnswer=EditableAnswer.yes;
    def setQuantityInCartEditionMode(EditableAnswer editableAnswer){
        this.editableAnswer = editableAnswer
    }

    boolean getEditable(){
        return editableAnswer==EditableAnswer.yes;
    }

    @Override
    def buildVue() {
        return null
    }
}

class ProductInCart implements Component{
    List<Component> componentList = new ArrayList<>();
    var deletable = false
    var totalComponent = false
    var totalLabel = "Sous-Total"

    def enableDeleteable(){
        deletable = true
    }

    def addTotalComponent(String totalLabel){
        totalComponent = true
        this.totalLabel = totalLabel
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

    @Override
    def buildVue() {
        return null
    }
}

trait OneLineAbleLabelledComponent implements Component{
    String label
    String componentName

    void setLabel(String label){
        this.label = label

    }

    @Override
    String toString() {
        return "${componentName} label ${label}"
    }
}


class PromoCode implements OneLineAbleLabelledComponent{
    PromoCode(){
        if (componentList ==null) componentList = new ArrayList<>()
        componentName = "PromoCode"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Remark implements OneLineAbleLabelledComponent{
    Remark(){
        if (componentList ==null) componentList = new ArrayList<>()
        componentName = "Remark"
    }

    @Override
    def buildVue() {
        return null
    }
}
class SubTotal implements OneLineAbleLabelledComponent{
    SubTotal(){
        componentName = "SubTotal"
    }

    @Override
    def buildVue() {
        return null
    }
}
class Total implements OneLineAbleLabelledComponent{
    Total(){
        componentName = "Total"
    }

    @Override
    def buildVue() {
        return null
    }
}

class DeliveryInCart implements Component {
    String label
    Integer defaultValue

    public DeliveryInCart(){
        if (componentList ==null) componentList = new ArrayList<>()
    }

    void setLabel(String label) {
        this.label = label
    }

    void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue
    }

    @Override
    String toString() {
        return  "                        Delivery{\n" +
                "                            label ${label}\n" +
                "                            default ${defaultValue}\n" +
                "                        }\n"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Summary implements Component {
    DeliveryInCart delivery
    String label
    SubTotal subTotal
    Total total

    public Summary(){
        if (componentList ==null) componentList = new ArrayList<>()
    }

    void setDelivery(DeliveryInCart delivery) {
        this.delivery = delivery
        addComponent(delivery)
    }

    void setLabel(String label) {
        this.label = label
        //TODO add String label as component
        //addComponent(label)
    }

    void setSubTotal(SubTotal subTotal) {
        this.subTotal = subTotal
        this.componentList = new ArrayList<>()
        addComponent(subTotal)
    }

    void setTotal(Total total) {
        this.total = total
        addComponent(total)
    }

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

    @Override
    def buildVue() {
        return null
    }
}

class Cart implements Component{

    String title
    ProductInCart productInCart
    PromoCode promoCode
    Remark remark
    Summary summary

    public Cart(){
        this.componentList = new ArrayList<>()
    }

    def setProductInCart(ProductInCart productInCart){
        this.productInCart = productInCart
        addComponent(productInCart)
    }
    def setPromoCode(PromoCode promoCode){
        this.promoCode = promoCode
        addComponent(promoCode)
    }
    def setRemark(Remark remark){
        this.remark = remark
        addComponent(remark)
    }
    def setSummary(Summary summary){
        this.summary = summary
        addComponent(summary)
    }
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

    @Override
    def buildVue() {
        return null
    }
}


enum DeletableAnswer{
    yes,
    no
}

enum EditableAnswer{
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
                for(Component component : (c.componentList)){
                    if (component instanceof Field){
                        var tmpField = new VueJsField()
                        tmpField.setName(component.name)
                        tmpField.setType(component.type)
                        vue.fields.add(tmpField)
                    }
                    if (component instanceof RadioGroup){
                        var tmpRadioGroup = new VueJsRadioGroup()
                        tmpRadioGroup.name = component.name;
                        for(Field f : (component.componentList as List<Field>)){
                            tmpRadioGroup.fields.add(f.buildVue())
                        }
                        vue.fields.add(tmpRadioGroup)
                    }
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
        VueJsField vueJsField = new VueJsField()
        vueJsField.setType(type)
        vueJsField.setName(name)
        return vueJsField
    }
}

class RadioGroup implements Component{
    String name;
    List<Component> componentList = new ArrayList<>();


    @Override
    def buildVue() {
        return null
    }

    @Override
    public String toString() {
        return "RadioGroup{" +
                "name='" + name + '\'' +
                ", componentList=" + componentList +
                '}';
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
}


trait Component implements ApplicationModelVisitable {
    List<Component> componentList

    def addComponent(Component component) {
        this.componentList.add(component)
    }

    String toString() {
        return "Component {components = ${componentList} }"
    }

    def buildVue(){

    }

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
    def visit(ProductInCart productInCart)
    def visit(Poster poster)
    def visit(MiniDescription miniDescription)
    def visit(QuantityInCart quantityInCart)
    def visit(Total total)
    def visit(SubTotal subTotal)
    def visit(PromoCode promoCode)
    def visit(Remark remark)
    def visit(Summary summary)
    def visit(DeliveryInCart deliveryInCart)

    def visit(Header header)

    def visit(WebApplication application)

    def visit(Catalog application)
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
    def visit(Filter filter)
    def visit(PriceFilter priceFilter)
    def visit(Product product)
    def visit(GenericFilters genericFilters)
    def visit(GenericFilter genericFilter)

    def visit(CartPreview cartPreview)
}

trait LeafComponent{

}

trait CompositeComponent{

}

