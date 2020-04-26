/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package middleware.inthouse;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-04-27")
public class CCTV {

  public interface Iface extends Device.Iface {

    public Image captureImage() throws NoData, org.apache.thrift.TException;

  }

  public interface AsyncIface extends Device .AsyncIface {

    public void captureImage(org.apache.thrift.async.AsyncMethodCallback<Image> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends Device.Client implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public Image captureImage() throws NoData, org.apache.thrift.TException
    {
      send_captureImage();
      return recv_captureImage();
    }

    public void send_captureImage() throws org.apache.thrift.TException
    {
      captureImage_args args = new captureImage_args();
      sendBase("captureImage", args);
    }

    public Image recv_captureImage() throws NoData, org.apache.thrift.TException
    {
      captureImage_result result = new captureImage_result();
      receiveBase(result, "captureImage");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.ex != null) {
        throw result.ex;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "captureImage failed: unknown result");
    }

  }
  public static class AsyncClient extends Device.AsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void captureImage(org.apache.thrift.async.AsyncMethodCallback<Image> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      captureImage_call method_call = new captureImage_call(resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class captureImage_call extends org.apache.thrift.async.TAsyncMethodCall<Image> {
      public captureImage_call(org.apache.thrift.async.AsyncMethodCallback<Image> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("captureImage", org.apache.thrift.protocol.TMessageType.CALL, 0));
        captureImage_args args = new captureImage_args();
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Image getResult() throws NoData, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new java.lang.IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_captureImage();
      }
    }

  }

  public static class Processor<I extends Iface> extends Device.Processor<I> implements org.apache.thrift.TProcessor {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> java.util.Map<java.lang.String,  org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("captureImage", new captureImage());
      return processMap;
    }

    public static class captureImage<I extends Iface> extends org.apache.thrift.ProcessFunction<I, captureImage_args> {
      public captureImage() {
        super("captureImage");
      }

      public captureImage_args getEmptyArgsInstance() {
        return new captureImage_args();
      }

      protected boolean isOneway() {
        return false;
      }

      @Override
      protected boolean rethrowUnhandledExceptions() {
        return false;
      }

      public captureImage_result getResult(I iface, captureImage_args args) throws org.apache.thrift.TException {
        captureImage_result result = new captureImage_result();
        try {
          result.success = iface.captureImage();
        } catch (NoData ex) {
          result.ex = ex;
        }
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends Device.AsyncProcessor<I> {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("captureImage", new captureImage());
      return processMap;
    }

    public static class captureImage<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, captureImage_args, Image> {
      public captureImage() {
        super("captureImage");
      }

      public captureImage_args getEmptyArgsInstance() {
        return new captureImage_args();
      }

      public org.apache.thrift.async.AsyncMethodCallback<Image> getResultHandler(final org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new org.apache.thrift.async.AsyncMethodCallback<Image>() { 
          public void onComplete(Image o) {
            captureImage_result result = new captureImage_result();
            result.success = o;
            try {
              fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (org.apache.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (java.lang.Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            captureImage_result result = new captureImage_result();
            if (e instanceof NoData) {
              result.ex = (NoData) e;
              result.setExIsSet(true);
              msg = result;
            } else if (e instanceof org.apache.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof org.apache.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (java.lang.Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, captureImage_args args, org.apache.thrift.async.AsyncMethodCallback<Image> resultHandler) throws org.apache.thrift.TException {
        iface.captureImage(resultHandler);
      }
    }

  }

  public static class captureImage_args implements org.apache.thrift.TBase<captureImage_args, captureImage_args._Fields>, java.io.Serializable, Cloneable, Comparable<captureImage_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("captureImage_args");


    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new captureImage_argsStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new captureImage_argsTupleSchemeFactory();


    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
;

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      @org.apache.thrift.annotation.Nullable
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      @org.apache.thrift.annotation.Nullable
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(captureImage_args.class, metaDataMap);
    }

    public captureImage_args() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public captureImage_args(captureImage_args other) {
    }

    public captureImage_args deepCopy() {
      return new captureImage_args(this);
    }

    @Override
    public void clear() {
    }

    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
      switch (field) {
      }
    }

    @org.apache.thrift.annotation.Nullable
    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof captureImage_args)
        return this.equals((captureImage_args)that);
      return false;
    }

    public boolean equals(captureImage_args that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      return hashCode;
    }

    @Override
    public int compareTo(captureImage_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      return 0;
    }

    @org.apache.thrift.annotation.Nullable
    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("captureImage_args(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class captureImage_argsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public captureImage_argsStandardScheme getScheme() {
        return new captureImage_argsStandardScheme();
      }
    }

    private static class captureImage_argsStandardScheme extends org.apache.thrift.scheme.StandardScheme<captureImage_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, captureImage_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, captureImage_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class captureImage_argsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public captureImage_argsTupleScheme getScheme() {
        return new captureImage_argsTupleScheme();
      }
    }

    private static class captureImage_argsTupleScheme extends org.apache.thrift.scheme.TupleScheme<captureImage_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, captureImage_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, captureImage_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

  public static class captureImage_result implements org.apache.thrift.TBase<captureImage_result, captureImage_result._Fields>, java.io.Serializable, Cloneable, Comparable<captureImage_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("captureImage_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);
    private static final org.apache.thrift.protocol.TField EX_FIELD_DESC = new org.apache.thrift.protocol.TField("ex", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new captureImage_resultStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new captureImage_resultTupleSchemeFactory();

    public @org.apache.thrift.annotation.Nullable Image success; // required
    public @org.apache.thrift.annotation.Nullable NoData ex; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      EX((short)1, "ex");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      @org.apache.thrift.annotation.Nullable
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // EX
            return EX;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      @org.apache.thrift.annotation.Nullable
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Image.class)));
      tmpMap.put(_Fields.EX, new org.apache.thrift.meta_data.FieldMetaData("ex", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, NoData.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(captureImage_result.class, metaDataMap);
    }

    public captureImage_result() {
    }

    public captureImage_result(
      Image success,
      NoData ex)
    {
      this();
      this.success = success;
      this.ex = ex;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public captureImage_result(captureImage_result other) {
      if (other.isSetSuccess()) {
        this.success = new Image(other.success);
      }
      if (other.isSetEx()) {
        this.ex = new NoData(other.ex);
      }
    }

    public captureImage_result deepCopy() {
      return new captureImage_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.ex = null;
    }

    @org.apache.thrift.annotation.Nullable
    public Image getSuccess() {
      return this.success;
    }

    public captureImage_result setSuccess(@org.apache.thrift.annotation.Nullable Image success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    @org.apache.thrift.annotation.Nullable
    public NoData getEx() {
      return this.ex;
    }

    public captureImage_result setEx(@org.apache.thrift.annotation.Nullable NoData ex) {
      this.ex = ex;
      return this;
    }

    public void unsetEx() {
      this.ex = null;
    }

    /** Returns true if field ex is set (has been assigned a value) and false otherwise */
    public boolean isSetEx() {
      return this.ex != null;
    }

    public void setExIsSet(boolean value) {
      if (!value) {
        this.ex = null;
      }
    }

    public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Image)value);
        }
        break;

      case EX:
        if (value == null) {
          unsetEx();
        } else {
          setEx((NoData)value);
        }
        break;

      }
    }

    @org.apache.thrift.annotation.Nullable
    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case EX:
        return getEx();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case EX:
        return isSetEx();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof captureImage_result)
        return this.equals((captureImage_result)that);
      return false;
    }

    public boolean equals(captureImage_result that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_ex = true && this.isSetEx();
      boolean that_present_ex = true && that.isSetEx();
      if (this_present_ex || that_present_ex) {
        if (!(this_present_ex && that_present_ex))
          return false;
        if (!this.ex.equals(that.ex))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
      if (isSetSuccess())
        hashCode = hashCode * 8191 + success.hashCode();

      hashCode = hashCode * 8191 + ((isSetEx()) ? 131071 : 524287);
      if (isSetEx())
        hashCode = hashCode * 8191 + ex.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(captureImage_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = java.lang.Boolean.valueOf(isSetEx()).compareTo(other.isSetEx());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetEx()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ex, other.ex);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    @org.apache.thrift.annotation.Nullable
    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
      }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("captureImage_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("ex:");
      if (this.ex == null) {
        sb.append("null");
      } else {
        sb.append(this.ex);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class captureImage_resultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public captureImage_resultStandardScheme getScheme() {
        return new captureImage_resultStandardScheme();
      }
    }

    private static class captureImage_resultStandardScheme extends org.apache.thrift.scheme.StandardScheme<captureImage_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, captureImage_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new Image();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // EX
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.ex = new NoData();
                struct.ex.read(iprot);
                struct.setExIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, captureImage_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.ex != null) {
          oprot.writeFieldBegin(EX_FIELD_DESC);
          struct.ex.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class captureImage_resultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public captureImage_resultTupleScheme getScheme() {
        return new captureImage_resultTupleScheme();
      }
    }

    private static class captureImage_resultTupleScheme extends org.apache.thrift.scheme.TupleScheme<captureImage_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, captureImage_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetEx()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
        if (struct.isSetEx()) {
          struct.ex.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, captureImage_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.success = new Image();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.ex = new NoData();
          struct.ex.read(iprot);
          struct.setExIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

}