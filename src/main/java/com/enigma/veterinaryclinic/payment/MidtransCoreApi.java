package com.enigma.veterinaryclinic.payment;

import com.midtrans.Config;
import com.midtrans.httpclient.error.MidtransError;
import org.json.JSONObject;

import java.util.Map;

public interface MidtransCoreApi {
  /**
   * Do re-set config Class like clientKey, serverKey, isProduction
   * @return {Config class}
   */
  Config apiConfig();

  /**
   * Do `/charge` API request to Core API
   * @param  {Map Object} parameter - object of Core API JSON body as parameter, will be converted to JSON (more params detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject chargeTransaction(Map<String, Object> params);

  /**
   * Do `/<orderId>/status` API request to Core API
   * @param  {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject checkTransaction(String orderId);

  /**
   * Do `/<orderId>/approve` API request to Core API
   * @param  {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject approveTransaction(String orderId);

  /**
   * Do `/<orderId>/cancel` API request to Core API
   * @param  {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject cancelTransaction(String orderId);

  /**
   * Do `/<orderId>/expire` API request to Core API
   * @param  {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject expireTransaction(String orderId);

  /**
   * Do `/<orderId>/refund` API request to Core API
   * @param  {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com)
   * @param  {Map Object} parameter - object of Core API JSON body as parameter, will be converted to JSON (more params detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject refundTransaction(String orderId, Map<String, String> params);

  /**
   * Do `/token` API request to Core API
   * @param  {Map Object} parameter - object of Core API JSON body as parameter, will be converted to JSON (more params detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject cardToken(Map<String, String> params);

  /**
   * Do `/card/register` API request to Core API
   * @param  {Map Object} parameter - object of Core API JSON body as parameter, will be converted to JSON (more params detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject registerCard(Map<String, String> params);

  /**
   * Do `/point_inquiry/<tokenId>` API request to Core API
   * @param  {String} tokenId - tokenId of credit card (more detail refer to: https://api-docs.midtrans.com)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject cardPointInquiry(String tokenId);

  /**
   * Do `/<orderId>/deny` API request to Core API
   * @param {String} orderId - orderId of the transaction (more detail refer to: https://api-docs.midtrans.com/#deny-transaction)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   */
  JSONObject denyTransaction(String orderId);

  /**
   * Do `v1/bins/{bin}` API request to Core API
   * @param binNumber {String} of the transaction (more detail refer to: https://api-docs.midtrans.com/#bin-api)
   * @return {JSONObject} - org.json Promise contains Object from JSON decoded response
   * @throws MidtransError when an exception was occurred during executing the request.
   */
  JSONObject getBIN(String binNumber) throws MidtransError;
}
