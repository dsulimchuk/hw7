package com.farata.course.mwd.auction.service;


import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class ProductServiceTest {
    /*private static WebTarget target;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class).
            addClass(AuctionRestApplication.class).
            addClass(DataEngine.class).
            addClass(Product.class)
            .addClass(ProductService.class);
        System.out.println(war.toString(true));

        return war;
    }

    @ArquillianResource
    private URL base;

    @Before
    public void setupClass() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "api/product").toExternalForm()));
    }


    @Test
    public void testGetCompanies() throws Exception {
        String r = target.request().get(String.class);
        assertEquals("", r);
    }

    @Test
    public void testGetCompanyById() throws Exception {
        String r = target.path("1").request().get(String.class);
        assertEquals("", r);
    }*/

}

