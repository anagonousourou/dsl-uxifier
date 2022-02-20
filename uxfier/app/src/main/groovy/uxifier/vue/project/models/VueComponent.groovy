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

        FileContext.writer.close()
        FileContext.writer = null


    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("<${this.name}/>")
    }

    @Override
    String toString() {
        return "VueComponent {name = ${name} } -> ${content}"
    }
}

class VueTemplateElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement {

}

trait HtmlElement {

}

trait LeafHtmlElement implements HtmlElement {

}

trait CompositeHtmlElement implements HtmlElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class HorizontalLayout implements HtmlElement {

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
    def insertInData(){}

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""
            <p> price filter type  ="${type}" </p><br>
        """)
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
    def insertInData(){
        priceFilter.insertInData()
        genericFilters.insertInData()
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""
            <h3> filter </h3>
            <div> priceFilter = """)
        priceFilter.insertInTemplate()
        FileContext.writer.write("""</div><br>
            <div> genericFilters = """)
        genericFilters.insertInTemplate()
        FileContext.writer.write("""
            </div><br>
        """)
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
    def insertInData(){
        for (VueGeneratable v: genericFilters){
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

    private final Product product

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
            <v-card elevation="2" outlined shaped tile>
                <v-card-title>{{product.name}}</v-card-title>
                <v-card-text> product rating ="${product.rating.ratingType}" </v-card-text><br>
            </v-card>
        """)
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
    def insertInData(){

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
    def writeTemplate() {
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
        filtre.insertInTemplate()
        FileContext.writer.write("""
            <h1> Context</h1>
            <h3> products </h3>
            <div v-for="product in products" :key="product.name">  """)

        product.insertInTemplate()

        FileContext.writer.write("""
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

class VueJsForm implements VueGeneratable{

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
        FileContext.writer.write("""<vaadin-form-layout name="${name}">""")

        fields.forEach(s -> s.insertInTemplate())

        FileContext.writer.write("""</vaadin-form-layout>""")
    }

    @Override
    public String toString() {
        return "VueJsForm{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}

class VueJsField implements VueGeneratable{

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
        FileContext.writer.write("""<vaadin-${type} label="${name}"/><br/>""")
    }
}

class VueJsAccordionGroup implements VueGeneratable{

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
        FileContext.writer.write("""<vaadin-accordion style="width:40%; margin-left: 2%; margin-right: 2%; margin-top: 2%">""")
        for(VueGeneratable v : accordions){
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

class VueJsAccordion implements VueGeneratable{
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
        for(VueGeneratable v : components){
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


trait VueGeneratable {

    //NOTE : deux contextes pour un composant un où on génère son propre fichier et un où il est utilisé dans un autre fichier


    def registerDependencies(PackageJson packageJson){

    }

    /**
     * Write own template
     */
    def writeTemplate() {

    }
    def insertInData(){

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
    def openTagInTemplate(){

    }

   def closeTagInTemplate(){

    }

    def toCode(VueProject project) {
        this.registerDependencies(project.packageJson)
        this.writeTemplate()
        this.writeScript()
        this.writeStyle()
        if(FileContext.writer != null && FileContext.writer){
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

