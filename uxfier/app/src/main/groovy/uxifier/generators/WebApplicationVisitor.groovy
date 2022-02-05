package uxifier.generators

import uxifier.models.WebApplication

interface WebApplicationVisitor {

    void visit(WebApplication application)
}