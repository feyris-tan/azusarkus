package moe.yo3explorer.azusa.js3db.boundary;

import fr.cryptohash.BMW256;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import moe.yo3explorer.azusa.js3db.entity.Js3DbUser;
import moe.yo3explorer.azusa.js3db.entity.Version;
import moe.yo3explorer.azusa.vapor.control.Hashdeep;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

@Path("/js3db-revival")
public class Js3DbRevivalResource {

    private static final String PEPPER = "Hehe, it's so warm.";
    private static final int MINIMUM_USER_ID = 41000;

    @Inject
    Template js3dbrevivalIndex;

    @Inject
    Template js3dbrevivalRegister;

    @Inject
    Logger logger;

    @Inject
    Random random;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance indexHtml()
    {
        return js3dbrevivalIndex.instance();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("testConnect.php")
    public String testConnectPhp()
    {
        return "0";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("ag3dbc/authenticate.php")
    public String authenticate(@FormParam("version") String version,@FormParam("username") String username, @FormParam("password") String password)
    {
        version = ag3dbcUnobfuscate(version);
        password = ag3dbcUnobfuscate(password);

        Version minimumRequiredVersion = new Version("1.01.2");
        Version requestingVersion = new Version(version);
        if (requestingVersion.compareTo(minimumRequiredVersion) == -1)
            return "5";

        Optional<Js3DbUser> js3DbUserProxy = Js3DbUser.find("username = ?1", username).firstResultOptional();
        if (!js3DbUserProxy.isPresent())
            return "1";

        Js3DbUser js3DbUser = js3DbUserProxy.get();
        String actualPassword = hashPassword(password,js3DbUser.salt,PEPPER);
        if (!js3DbUser.password.equals(actualPassword))
            return "1";

        return String.format("0|%d|%s|%s",js3DbUser.id,username,ag3dbcObfuscate(password));
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/js3dbRegister")
    @Transactional
    public TemplateInstance register(@FormParam("email") String email, @FormParam("username") String username, @FormParam("password") String password)
    {
        logger.infof("Register user %s (%s) with password %s",username,email,password);

        Optional<Js3DbUser> foundByUsername = Js3DbUser.find("username = ?1", username).firstResultOptional();
        if (foundByUsername.isPresent())
            return js3dbrevivalRegister.data("message","Username is already taken!");
        Optional<Js3DbUser> foundByEmail = Js3DbUser.find("email = ?1", email).firstResultOptional();
        if (foundByEmail.isPresent())
            return js3dbrevivalRegister.data("message","A user already exists with that e-Mail!");

        Optional<Js3DbUser> latestUser = Js3DbUser.findAll(Sort.descending("id")).firstResultOptional();
        int newUserId = 0;
        if (latestUser.isPresent())
            newUserId = latestUser.get().id + 1;
        else
            newUserId = MINIMUM_USER_ID;

        Js3DbUser newUser = new Js3DbUser();
        newUser.id = newUserId;
        newUser.username = username;
        newUser.salt = new BigInteger(100,random).toString(26);
        newUser.password = hashPassword(password,newUser.salt,PEPPER);
        newUser.email = email;
        newUser.persistAndFlush();

        String outmsg = String.format("You can now log-in with AG3DBC using the username %s and your supplied password.",username);
        return js3dbrevivalRegister.data("message",outmsg);
    }

    public String ag3dbcUnobfuscate(String input)
    {
        byte[] bytes = Hashdeep.hexToBytes(input);
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)~bytes[i];
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    public String ag3dbcObfuscate(String input)
    {
        byte[] bytes = input.getBytes(StandardCharsets.US_ASCII);
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)~bytes[i];
        return Hashdeep.bytesToHex(bytes);
    }

    public String hashPassword(String password, String salt, String pepper)
    {
        BMW256 bmw256 = new BMW256();
        bmw256.update(pepper.getBytes());
        bmw256.update(salt.getBytes());
        bmw256.update(password.getBytes());
        return Hashdeep.bytesToHex(bmw256.digest());
    }
}
