package uxifier.models.visitors

import uxifier.models.ApplicationModelVisitor
import uxifier.models.Catalog
import uxifier.models.Component
import uxifier.models.Filter
import uxifier.models.GenericFilter
import uxifier.models.GenericFilters
import uxifier.models.Header
import uxifier.models.HorizontalLayout
import uxifier.models.Menu
import uxifier.models.NavigationMenu
import uxifier.models.PriceFilter
import uxifier.models.Product
import uxifier.models.Rating
import uxifier.models.SocialMedia
import uxifier.models.SocialMediaGroup
import uxifier.models.WebApplication
import uxifier.models.WebPage
import uxifier.vue.project.models.VueComponent
import uxifier.vue.project.models.VueGeneratable
import uxifier.vue.project.models.VueJsCatalog
import uxifier.vue.project.models.VueJsFilter
import uxifier.vue.project.models.VueJsGenericFilter
import uxifier.vue.project.models.VueJsGenericFilters
import uxifier.vue.project.models.VueJsPriceFilter
import uxifier.vue.project.models.VueJsProduct
import uxifier.vue.project.models.VueJsRating
import uxifier.vue.project.models.VueJsSocialMedia
import uxifier.vue.project.models.VueJsSocialMediaGroup
import uxifier.vue.project.models.VueMenu
import uxifier.vue.project.models.VueMenuBar
import uxifier.vue.project.models.VueProject

class ApplicationModelVisitorVueJS implements ApplicationModelVisitor {
    int count = 1

    VueProject vueProject = new VueProject()

    private VueGeneratable parent

    @Override
    def visit(SocialMedia media) {
        var tmp = new VueJsSocialMedia(media.type.toString(), media.url)
        this.parent.addContent(tmp)
    }

    @Override
    def visit(HorizontalLayout layout) {
        return null
    }

    @Override
    def visit(Component component) {
        return null
    }

    @Override
    def visit(SocialMediaGroup socialMediaGroup) {
        var tmp = new VueJsSocialMediaGroup()

        this.parent.addContent(tmp)
        this.parent = tmp
        socialMediaGroup.componentList.forEach(c -> c.accept(this))
    }

    @Override
    def visit(Header header) {
        return null
    }

    @Override
    def visit(WebApplication application) {
        //idée plusieurs passages dans l'arbre, premier passage : trouver les dependencies et set certaines informations triviales,
        //passages supplémentaires pour résoudre des liens, du routing ...
        this.vueProject.name = application.name
        this.vueProject.packageJson.name = application.name

        for (WebPage webPage : application.pages) {
            webPage.accept(this)
        }

    }

    @Override
    def visit(Catalog catalog) {

        println("inside catalog ==========" + catalog)
        var tmp = new VueJsCatalog()

        this.parent.addContent(tmp)
        var previousParent = this.parent
        this.parent = tmp
        catalog.filter.accept(this)
        catalog.product.accept(this)
        this.parent = previousParent

    }


    @Override
    def visit(PriceFilter priceFilter) {
        var tmp = new VueJsPriceFilter(priceFilter.priceType)

        ((VueJsFilter) this.parent).priceFilter = tmp
    }

    @Override
    def visit(Filter filter) {

        println("inside filter ==========" + filter)
        var tmp = new VueJsFilter()

        ((VueJsCatalog) this.parent).filtre = tmp

        var previousParent = this.parent
        this.parent = tmp
        filter.priceFilter.accept(this)
        filter.genericFilters.accept(this)
        this.parent = previousParent

    }


    @Override
    def visit(Product product) {

        println("inside product ==========" + product)
        var tmp = new VueJsProduct(product)
        ((VueJsCatalog) this.parent).product = tmp
    }

    @Override
    def visit(GenericFilters genericFilters) {
        var tmp = new VueJsGenericFilters()

        ((VueJsFilter)this.parent).genericFilters = tmp
        var previousParent = this.parent
        this.parent = tmp
        genericFilters.componentList.forEach(c -> c.accept(this))
        this.parent = previousParent
    }

    @Override
    def visit(GenericFilter genericFilter) {

        println("inside product ==========" + genericFilter)


        var tmp = new VueJsGenericFilter(genericFilter.targetAtributType, genericFilter.targetAtributName)

        this.parent.addContent(tmp)


    }

    @Override
    def visit(WebPage webPage) {

        VueComponent vueComponent = new VueComponent()
        vueComponent.name = webPage.name

        var previousParent = this.parent
        this.parent = vueComponent

        for (Component component1 : webPage.getComponentList()) {
            component1.accept(this)
        }

        this.vueProject.addVueComponent(vueComponent)

        //Given the component is a webpage (the only one for now) we add it as content of App.vue

        this.vueProject.sourceDirectory.appFile.content.add(vueComponent)

        this.parent = previousParent
    }

    def visit(NavigationMenu navigationMenu) {
        VueMenuBar menuBar = new VueMenuBar()

        var tmp = this.parent
        this.parent = menuBar

        for (Component comp : navigationMenu.componentList) {
            comp.accept(this)
        }

        this.vueProject.sourceDirectory.appFile.content.add(menuBar)

        this.parent = tmp

        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core', '22.0.5')
    }

    @Override
    def visit(Menu menu) {
        VueMenu vueMenu = new VueMenu()
        vueMenu.link = menu.link
        vueMenu.label = menu.label
        this.parent.addContent(vueMenu)
    }


}
