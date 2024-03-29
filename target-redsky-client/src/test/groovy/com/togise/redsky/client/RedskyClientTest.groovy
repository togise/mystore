package com.togise.redsky.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.togise.http.client.HttpClient
import spock.lang.Specification

import static com.togise.redsky.client.RedskyClient.*

class RedskyClientTest extends Specification {

    String testJson = '''{"product":{"deep_red_labels":{"total_count":2,"labels":[{"id":"twbl94","name":"Movies","type":"merchandise type","priority":0,"count":1},{"id":"gqwm8i","name":"TAC","type":"relationship type","priority":0,"count":1}]},"available_to_promise_network":{"product_id":"13860428","id_type":"TCIN","available_to_promise_quantity":32,"street_date":"2011-11-15T06:00:00.000Z","availability":"AVAILABLE","online_available_to_promise_quantity":32,"stores_available_to_promise_quantity":0,"availability_status":"IN_STOCK","multichannel_options":[],"is_infinite_inventory":false,"loyalty_availability_status":"IN_STOCK","loyalty_purchase_start_date_time":"1970-01-01T00:00:00.000Z","is_loyalty_purchase_enabled":false,"is_out_of_stock_in_all_store_locations":true,"is_out_of_stock_in_all_online_locations":false},"item":{"tcin":"13860428","bundle_components":{"is_assortment":false,"is_kit_master":false,"is_standard_item":true,"is_component":false},"dpci":"058-34-0436","upc":"025192110306","product_description":{"title":"The Big Lebowski (Blu-ray)","bullet_description":["<B>Movie Studio:</B> Universal Studios","<B>Movie Genre:</B> Comedy","<B>Software Format:</B> Blu-ray"]},"buy_url":"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428","variation":{},"enrichment":{"images":[{"base_url":"https://target.scene7.com/is/image/Target/","primary":"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e","content_labels":[{"image_url":"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e"}]}],"sales_classification_nodes":[{"node_id":"5xswx"},{"node_id":"hp0vg"}]},"return_method":"This item can be returned to any Target store or Target.com.","handling":{},"recall_compliance":{"is_product_recalled":false},"tax_category":{"tax_class":"G","tax_code_id":99999,"tax_code":"99999"},"display_option":{"is_size_chart":false,"is_warranty":false},"fulfillment":{"is_po_box_prohibited":true,"po_box_prohibited_message":"We regret that this item cannot be shipped to PO Boxes."},"package_dimensions":{"weight":"0.18","weight_unit_of_measure":"POUND","width":"5.33","depth":"6.65","height":"0.46","dimension_unit_of_measure":"INCH"},"environmental_segmentation":{"is_lead_disclosure":false},"manufacturer":{},"product_vendors":[{"id":"4667999","manufacturer_style":"61119422","vendor_name":"UNIVERSAL HOME VIDEO"},{"id":"1258738","manufacturer_style":"025192110306","vendor_name":"BAKER AND TAYLOR"},{"id":"1979650","manufacturer_style":"61119422","vendor_name":"Universal Home Ent PFS"}],"product_classification":{"product_type":"542","product_type_name":"ELECTRONICS","item_type_name":"Movies","item_type":{"category_type":"Item Type: MMBV","type":300752,"name":"Movies"}},"product_brand":{"brand":"Universal Home Video","facet_id":"55zki"},"item_state":"READY_FOR_LAUNCH","specifications":[],"attributes":{"gift_wrapable":"N","has_prop65":"N","is_hazmat":"N","max_order_qty":10,"street_date":"2011-11-15","media_format":"Blu-ray","merch_class":"MOVIES","merch_classid":58,"merch_subclass":34,"return_method":"This item can be returned to any Target store or Target.com."},"country_of_origin":"US","relationship_type_code":"Title Authority Child","subscription_eligible":false,"ribbons":[],"tags":[],"estore_item_status_code":"A","is_proposition_65":false,"return_policies":{"user":"Regular Guest","policyDays":"30","guestMessage":"This item must be returned within 30 days of the ship date. See return policy for details."},"gifting_enabled":false}}}'''

    HttpClient httpClient = Mock()
    String baseRequestUrl = 'http://hello/$s'

    int id = 1234

    def "test getProductName"() {
        NamingClient namingClient = new RedskyClient(httpClient, baseRequestUrl, new ObjectMapper())

        when:
        String name = namingClient.getProductName(id)

        then:
        1 * httpClient.get(String.format(baseRequestUrl, id)) >> new ByteArrayInputStream(testJson.getBytes())
        name == "The Big Lebowski (Blu-ray)"
    }

    def "test exception"() {
        ObjectMapper mapper = Mock()
        mapper.readTree(_) >> {InputStream is -> throw new IOException("Oops! It failed")}
        NamingClient namingClient = new RedskyClient(httpClient, baseRequestUrl, mapper)

        when:
        namingClient.getProductName(id)

        then:
        thrown RedskyClientJsonParseException
    }
}
