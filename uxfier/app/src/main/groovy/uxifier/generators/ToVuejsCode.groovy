package uxifier.generators

import uxifier.models.WebApplication
import uxifier.vue.project.models.VueProject


class ToVuejsCode implements WebApplicationVisitor {

    int passNumber = 1;

    VueProject vueProject = new VueProject();

    void visit(WebApplication application) {
        this.vueProject.name = application.name;

    }
}
