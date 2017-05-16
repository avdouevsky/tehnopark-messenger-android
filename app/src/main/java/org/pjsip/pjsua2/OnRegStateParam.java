/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class OnRegStateParam {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected OnRegStateParam(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OnRegStateParam obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_OnRegStateParam(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setStatus(int value) {
    pjsua2JNI.OnRegStateParam_status_set(swigCPtr, this, value);
  }

  public int getStatus() {
    return pjsua2JNI.OnRegStateParam_status_get(swigCPtr, this);
  }

  public void setCode(pjsip_status_code value) {
    pjsua2JNI.OnRegStateParam_code_set(swigCPtr, this, value.swigValue());
  }

  public pjsip_status_code getCode() {
    return pjsip_status_code.swigToEnum(pjsua2JNI.OnRegStateParam_code_get(swigCPtr, this));
  }

  public void setReason(String value) {
    pjsua2JNI.OnRegStateParam_reason_set(swigCPtr, this, value);
  }

  public String getReason() {
    return pjsua2JNI.OnRegStateParam_reason_get(swigCPtr, this);
  }

  public void setRdata(SipRxData value) {
    pjsua2JNI.OnRegStateParam_rdata_set(swigCPtr, this, SipRxData.getCPtr(value), value);
  }

  public SipRxData getRdata() {
    long cPtr = pjsua2JNI.OnRegStateParam_rdata_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SipRxData(cPtr, false);
  }

  public void setExpiration(int value) {
    pjsua2JNI.OnRegStateParam_expiration_set(swigCPtr, this, value);
  }

  public int getExpiration() {
    return pjsua2JNI.OnRegStateParam_expiration_get(swigCPtr, this);
  }

  public OnRegStateParam() {
    this(pjsua2JNI.new_OnRegStateParam(), true);
  }

}