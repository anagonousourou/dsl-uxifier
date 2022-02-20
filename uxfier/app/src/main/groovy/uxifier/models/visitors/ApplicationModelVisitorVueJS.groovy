package uxifier.models.visitors

import uxifier.models.*
import uxifier.vue.project.models.*

class ApplicationModelVisitorVueJS implements  ApplicationModelVisitor{
    int count = 1

    VueProject vueProject=new VueProject()

    private VueGeneratable parent

    private NavigationMenuType currentNavigationMenuType = null

    @Override
    def visit(SocialMedia media) {
        var tmp = new VueJsSocialMedia(media.type.toString(), media.url)
        this.parent.addContent(tmp)
        return tmp
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
        var previousParent = this.parent
        var tmp = new VueJsSocialMediaGroup()
        this.parent.addContent(tmp)
        this.parent  = tmp // Pourquoi cette ligne ?
        socialMediaGroup.componentList.forEach(c -> c.accept(this))
        this.parent = previousParent
        return tmp
    }

    @Override
    def visit(Form form){
        var tmp = form.buildVue()
        this.parent.addContent(tmp)
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core','22.0.5')
        return tmp
    }

    @Override
    def visit(Field field){
        return null
    }

    @Override
    def visit(AccordionGroup accordionGroup){
        var tmp = accordionGroup.buildVue()
        this.parent.addContent(tmp)
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core','22.0.5')
        return tmp
    }

    @Override
    def visit(Accordion accordion){
        return null
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
        this.vueProject.pageTitle = application.title
        this.vueProject.packageJson.name = application.name
        if(application.navigationMenu != null){
            application.navigationMenu.accept(this)
        }
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

        println("inside generic filter ==========" + genericFilter)


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
        this.currentNavigationMenuType = navigationMenu.menuType
        if (this.currentNavigationMenuType == NavigationMenuType.Navbar) {
            VueMenuNavbar menuNavbar = new VueMenuNavbar()
            menuNavbar.setApplicationName(navigationMenu.getApplicationName())
            this.parent = menuNavbar

            for (Component comp : navigationMenu.componentList) {
                comp.accept(this)
            }
            this.vueProject.sourceDirectory.appFile.content.add(menuNavbar)

        } else if (this.currentNavigationMenuType == NavigationMenuType.Drawer) {
            VueMenuBar menuBar = new VueMenuBar()
            menuBar.setApplicationName(navigationMenu.getApplicationName())

            this.parent = menuBar

            for (Component comp : navigationMenu.componentList) {
                comp.accept(this)
            }
            this.vueProject.sourceDirectory.appFile.content.add(menuBar)
        }
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core', '22.0.5')
    }

    @Override
    def visit(Menu menu) {
        if (this.currentNavigationMenuType == NavigationMenuType.Drawer) {
            VueMenu vueMenu = new VueMenu()
            vueMenu.link = menu.link
            vueMenu.label = menu.label
            vueMenu.icon = menu.icon
            this.parent.addContent(vueMenu)
        } else {
            VueMenuItemNavbar vueMenu = new VueMenuItemNavbar()
            vueMenu.link = menu.link
            vueMenu.label = menu.label
            this.parent.addContent(vueMenu)
        }

    }

    @Override
    def visit(Action action) {
        VueActionMenu actionMenu = new VueActionMenu()
        actionMenu.label = action.label
        this.parent.addContent(actionMenu)
        println("Adding action")
    }

    @Override
    def visit(ActionMenuBar menuBar) {

        println("Adding actionMenuBar")
        var previousParent = this.parent
        var vueActionMenuBar = new VueActionMenuBar()

        this.parent = vueActionMenuBar
        menuBar.componentList.forEach(c -> c.accept(this))

        this.parent = previousParent

        this.vueProject.sourceDirectory.appFile.content.add(vueActionMenuBar)

    }

    @Override
    def visit(CartAction action) {
        this.parent.addContent(new VueCartActionMenu(action.label, action.displayCartCount, action.displayCartIcon))
        println("Adding cartaction to ${this.parent}")
    }
}
