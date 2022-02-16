package uxifier.vue.project.models

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.ToString
import uxifier.models.Catalog
import uxifier.models.Filter
import uxifier.models.PriceFilter
import uxifier.models.PriceType
import uxifier.models.Product
import uxifier.models.Rating
import uxifier.models.RatingType

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
        FileContext.writer.write("<script>")
        content.forEach(c -> c.insertSelfInImports())
        FileContext.writer.write("</script>")

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
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""
            <h3> filter </h3>
            <div> priceFilter = "${priceFilter.insertInTemplate()}"</div><br>
            <div> genericFilters = "${genericFilters.insertInTemplate()}"</div><br>
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
            <h1> Context</h1>
            <h3> product </h3>
            <p> product rating ="${product.rating.ratingType}" </p><br>
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
            <div v-for="product in products">  """)

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

trait VueGeneratable {

    //NOTE : deux contextes pour un composant un où on génère son propre fichier et un où il est utilisé dans un autre fichier


    abstract def registerDependencies(PackageJson packageJson)

    /**
     * Write own template
     */
    def writeTemplate() {

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

    def toCode(VueProject project) {
        this.registerDependencies(project.packageJson)
        this.writeTemplate()
        this.writeScript()
        this.writeStyle()
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
