<template>

  <vaadin-horizontal-layout style="width: 100%;">

    <vaadin-vertical-layout style="width: 100%;">
      <vaadin-vertical-layout style="width: 100%;">
        <vaadin-label>Mon Panier</vaadin-label>

        <hr style="width: 100%;"/>

        <vaadin-horizontal-layout style="width: 100%;">
          <img id="myimage" src="https://picsum.photos/200" width="133" height="100">

          <vaadin-vertical-layout
              style="justify-content: flex-start; padding-left: 5px;">
            <layout-item><span>Je suis un article</span></layout-item>
            <layout-item>
              <vaadin-number-field edit="false" value="400" readonly>
                <div slot="suffix">€</div>
              </vaadin-number-field>
            </layout-item>
            <layout-item>
              <vaadin-integer-field has-controls class="smallScreen" max="100000" min="0" value="2"></vaadin-integer-field>
            </layout-item>
          </vaadin-vertical-layout>

          <vaadin-horizontal-layout class="largeScreen" style="justify-content: space-evenly; flex-grow: 2;">

            <vaadin-integer-field has-controls   max="100000" min="0" value="2"></vaadin-integer-field>

            <layout-item style="justify-content: space-around;" >
              <vaadin-number-field value="400"  readonly >
                <div slot="suffix">€</div>
              </vaadin-number-field>
            </layout-item>

            <vaadin-button aria-label="Close" theme="icon">
              <vaadin-icon icon="vaadin:close-small"></vaadin-icon>
            </vaadin-button>

          </vaadin-horizontal-layout>

          <vaadin-vertical-layout class="smallScreen" style="justify-content: space-between; flex-grow: 2;">

            <layout-item style="padding-left: 40%">
              <vaadin-button aria-label="Close" theme="icon">
                <vaadin-icon icon="vaadin:close-small"></vaadin-icon>
              </vaadin-button>
            </layout-item>


            <layout-item style="padding-left: 2%">
              <vaadin-number-field value="400"  readonly >
                <div slot="suffix">€</div>
              </vaadin-number-field>
            </layout-item>

          </vaadin-vertical-layout>
        </vaadin-horizontal-layout>
      </vaadin-vertical-layout>

      <vaadin-vertical-layout>
        <vaadin-horizontal-layout>

          <vaadin-text-field label="Saisissez un code Promo">
            <vaadin-icon icon="vaadin:ticket"></vaadin-icon>
          </vaadin-text-field>

          <vaadin-vertical-layout style="justify-content: end;">
            <vaadin-button >Appliquer</vaadin-button>
          </vaadin-vertical-layout>

        </vaadin-horizontal-layout>

        <vaadin-text-area
            .maxlength="1233"
            .value="toto"
            label="Ajouter une remarque"
            width="100"
        >
          <vaadin-icon icon="vaadin:file-text-o"></vaadin-icon>
        </vaadin-text-area>
      </vaadin-vertical-layout>

      <vaadin-vertical-layout style="width: 100%; padding-top: 20px;" class="smallScreen">

        <vaadin-label>Résumé de la commande</vaadin-label>

        <hr style="width: 100%;"/>


        <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

          <vaadin-label>Sous-total</vaadin-label>

          <layout-item style="padding-left: 2%">
            <vaadin-number-field value="400"  readonly >
              <div slot="suffix">€</div>
            </vaadin-number-field>
          </layout-item>
        </vaadin-horizontal-layout>

        <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

          <vaadin-label>Frais d'expédition estimés</vaadin-label>

          <layout-item style="padding-left: 2%">
            <vaadin-number-field value="400"  readonly >
              <div slot="suffix">€</div>
            </vaadin-number-field>
          </layout-item>
        </vaadin-horizontal-layout>

        <hr style="width: 100%;"/>

        <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

          <vaadin-label>Total</vaadin-label>

          <layout-item style="padding-left: 2%">
            <vaadin-number-field value="400"  readonly >
              <div slot="suffix">€</div>
            </vaadin-number-field>
          </layout-item>
        </vaadin-horizontal-layout>

        <vaadin-button class="btn btn-primary" style="justify-content: center; width: 100%;" theme="secondary">Paiement</vaadin-button>

      </vaadin-vertical-layout>
    </vaadin-vertical-layout>

    <vaadin-vertical-layout style="width: 40%;padding-left: 40px;" class="largeScreen">

      <vaadin-label>Résumé de la commande</vaadin-label>

      <hr style="width: 100%;"/>


      <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

        <vaadin-label>Sous-total</vaadin-label>

        <layout-item style="padding-left: 2%">
          <vaadin-number-field value="400"  readonly >
            <div slot="suffix">€</div>
          </vaadin-number-field>
        </layout-item>
      </vaadin-horizontal-layout>

      <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

        <vaadin-label>Frais d'expédition estimés</vaadin-label>

        <layout-item style="padding-left: 2%">
          <vaadin-number-field value="400"  readonly >
            <div slot="suffix">€</div>
          </vaadin-number-field>
        </layout-item>
      </vaadin-horizontal-layout>

      <hr style="width: 100%;"/>

      <vaadin-horizontal-layout style="justify-content: space-between;width: 100%;">

        <vaadin-label>Total</vaadin-label>

        <layout-item style="padding-left: 2%">
          <vaadin-number-field value="400"  readonly >
            <div slot="suffix">€</div>
          </vaadin-number-field>
        </layout-item>
      </vaadin-horizontal-layout>

      <vaadin-button class="btn btn-primary" style="justify-content: center; width: 100%;" theme="secondary">Paiement</vaadin-button>

    </vaadin-vertical-layout>
  </vaadin-horizontal-layout>


</template>

<style>
/* Set the background color of body to tan */
body {
  //background-color: tan;
}

.largeScreen{
  visibility: visible;
}
.smallScreen{
  visibility: collapse;
}

/* On screens that are 992px or less, set the background color to blue */
@media screen and (max-width: 992px) {
  body {
    //background-color: blue;
  }

  .largeScreen{
    visibility: collapse;
  }
  .smallScreen{
    visibility: visible;
  }
}

/* On screens that are 600px or less, set the background color to olive */
/*@media screen and (max-width: 600px) {
  body {
    background-color: olive;
  }
}*/
</style>

<script>

</script>