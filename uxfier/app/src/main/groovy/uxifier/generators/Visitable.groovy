package uxifier.generators

interface Visitable {
    void accept(WebApplicationVisitor visitor)
}