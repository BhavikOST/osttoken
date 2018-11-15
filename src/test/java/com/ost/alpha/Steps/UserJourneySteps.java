package com.ost.alpha.Steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Calendar;
import java.util.TimeZone;

import cucumber.deps.com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import static io.restassured.RestAssured.given;

public class UserJourneySteps {

    final private static String secretKey = "be317460ffcded1c517701638411fa7cbe307f760a55aaa250199a99643f7fd3";
    final private static String apiKey = "8734d810982cadabc745";
    final private static String companyId = "5eafe35d-5d0e-49e5-8923-568d68aedfd9";
    static String request_timeStamp = getTimeStamp();
    public static HttpURLConnection conn;


    public static String customerName;
    public static String customerId;
    public static int token_balance;

    public static void main(String[] args)
    {
       // rewardTransactionExecute();
    }

    @Given("^New user created and his/her name is (.+)")
    public void newUserCreation(String name)  {

        if(name.isEmpty())
        {
            System.out.println("Please provide the Customer name");
            Assert.fail();
        }
        else
        {
            customerName=name;
        }
        //Deriving Signature
        String signature = null;
        String signatureData = "/users/create?api_key="+apiKey+"&name="+customerName+"&request_timestamp=" + request_timeStamp;
        try {
              signature = new String(encode(secretKey, signatureData));
        } catch (Exception e) {
            e.printStackTrace();
        }



        URL url;
        try {
            url = new URL("https://playgroundapi.ost.com/users/create?");
            String urlParameters =
                    "api_key=" + URLEncoder.encode(apiKey, "UTF-8") +
                            "&name=" + URLEncoder.encode(customerName, "UTF-8")+
                            "&request_timestamp="+ URLEncoder.encode(request_timeStamp, "UTF-8")+
                            "&signature=" +  URLEncoder.encode(signature, "UTF-8");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", ""+Integer.toString(urlParameters.getBytes().length));
            conn.setUseCaches (false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (
                    conn.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();


            System.out.println("Response message from Server : " +conn.getResponseMessage());
            String output;
            BufferedReader br;
            if ( conn.getResponseCode()==200) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            }
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                JSONObject responseJson  = new JSONObject(output);

                if(responseJson.getBoolean("success")==true)
                {
                    JSONArray jsonArray = responseJson.getJSONObject("data").getJSONArray("economy_users");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    customerId = jsonObject.getString("uuid");
                    token_balance = Integer.parseInt(jsonObject.getString("token_balance"));
                    System.out.println("New User Created successfully with Name : "+name);
                    System.out.println("UUID of "+name +" : "+ customerId);
                    System.out.println("Token Balance of "+name +" : "+ token_balance);
                }
                else
                {
                    System.out.println("API call unsuccessful. Please read below error messages");
                    System.out.println("Response message : "+responseJson.getJSONObject("err").getString("msg"));
                    System.out.println("Error Data : "+responseJson.getJSONObject("err").getJSONObject("error_data"));
                    Assert.fail();
                }
            }
        }

        catch (MalformedURLException e) {

            System.out.println("API call Unsuccessful");
            e.printStackTrace();
            Assert.fail();

        } catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("Exception protocol");
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO protocol");
            Assert.fail();
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Json Exception");
            Assert.fail();
        } finally {
            conn.disconnect();
        }
    }


    @When("^He/She got reward from Company$")
    public static void rewardTransactionExecute() throws InterruptedException {
        String signatureData = "/transaction-types/execute?api_key="+apiKey+"&from_uuid="+companyId+"&request_timestamp="+request_timeStamp+"&to_uuid="+customerId+"&transaction_kind=Reward";

        String signature = null;
        try {
            signature = new String(encode(secretKey, signatureData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        URL url;
        try {
            url = new URL("https://playgroundapi.ost.com/transaction-types/execute?");
            String urlParameters =
                    "api_key=" + URLEncoder.encode(apiKey, "UTF-8") +
                            "&from_uuid=" + URLEncoder.encode(companyId, "UTF-8")+
                            "&request_timestamp="+ URLEncoder.encode(request_timeStamp, "UTF-8")+
                            "&signature=" +  URLEncoder.encode(signature, "UTF-8")+
                            "&to_uuid="+ URLEncoder.encode(customerId, "UTF-8")+
                            "&transaction_kind="+ URLEncoder.encode("Reward", "UTF-8");


            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", ""+Integer.toString(urlParameters.getBytes().length));
            conn.setUseCaches (false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (
                    conn.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            System.out.println(conn.getResponseMessage());
            String output;
            BufferedReader br;
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            }
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {

                JSONObject responseJson  = new JSONObject(output);

                if(responseJson.getBoolean("success")==true)
                {
                    System.out.println("Transaction Placed successfully");
                    System.out.println("Transaction ID is : "+responseJson.getJSONObject("data").getString("transaction_uuid"));
                }
                else
                {
                    System.out.println("Transaction is not completed successfully because of below reason");
                    System.out.println("Response message : "+responseJson.getJSONObject("err").getString("msg"));
                    System.out.println("Error Data : "+responseJson.getJSONObject("err").getJSONObject("error_data"));
                    Assert.fail();
                }
            }
        }
        catch (MalformedURLException e) {

            System.out.println("API call Unsuccessful");
            e.printStackTrace();
            Assert.fail();

        } catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("Exception protocol");
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO protocol");
            Assert.fail();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();

        }

    }

    @Then("^His/Her BQA balance should increased by (.+)$")
    public static void getUserList(int balance) throws InterruptedException {
        Thread.sleep(1000);
        request_timeStamp = getTimeStamp();
        String signatureData = "/users/list?api_key="+apiKey+"&order_by=creation_time&page_no=1&request_timestamp="+request_timeStamp;
        String signature = null;
        try {
            signature = new String(encode(secretKey, signatureData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            String output = given()
                .contentType("application/json")
                .when()
                .get("https://playgroundapi.ost.com" + signatureData + "&signature=" + signature)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();


            JSONObject responseJson  = new JSONObject(output);
            if(responseJson.getBoolean("success")==true) {
                JSONArray jsonArray = responseJson.getJSONObject("data").getJSONArray("economy_users");
                for(int i =0; i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString("name").equals(customerName) && jsonObject.getString("uuid").equals(customerId))
                    {
                        token_balance = Integer.parseInt(jsonObject.getString("token_balance"));
                        Assert.assertEquals(balance,token_balance);
                        System.out.println("Updated balance of "+customerName+ " is : " +token_balance );
                        break;
                    }
                    else
                    {
                        System.out.println("Customer not found");
                        Assert.fail();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * Get the current EPOCH time in seconds
     * @return EPOCH seconds in String
     */
    public static String getTimeStamp()
    {
         Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
         long secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;
         String time = new String(String.valueOf(secondsSinceEpoch));
         return time;
    }

    /**
     * This will give you signature from provided secret key and
     * @param secret - User's Secret key in String format
     * @param data - Query to derived signature in String format
     * @return - Char[] bytes of encoded signature
     * @throws Exception
     */
    public static char[] encode(String secret, String data) throws Exception {
        byte[] hmacData = null;
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        hmacData = mac.doFinal(data.getBytes("UTF-8"));

        return Hex.encodeHex(hmacData);
        //return new Base64Encoder().encode(hmacData);
    }

}
