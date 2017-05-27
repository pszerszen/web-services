package com.osa.client.ws;

import com.google.common.base.Stopwatch;
import com.osa.client.ResponseWrapper;
import com.osa.model.DestinationsRequest;
import com.osa.model.HeartBeatRequest;
import com.osa.model.HeartBeatResponse;
import com.osa.model.Network;
import com.osa.model.NetworkRequest;
import com.osa.model.OriginsRequest;
import com.osa.model.StationList;
import com.osa.model.Trip;
import com.osa.model.TripRequest;
import com.osa.parsers.SoapParser;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SoapClient extends WebServiceGatewaySupport {

    private static final String EXAMPLE_HEADER = "<SOAP-ENV:Header>\n" +
            "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\">\n" +
            "         <wsse:UsernameToken wsu:Id=\"UsernameToken-EC9F7473E024359C6A14589178984712\">\n" +
            "            <wsse:Username>admin</wsse:Username>\n" +
            "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">Uq6pANLo2rGD1rfnOlvGHVHq7m0=</wsse:Password>\n" +
            "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">VNRqB4w0jhpDz92hdpjXkQ==</wsse:Nonce>\n" +
            "            <wsu:Created>2016-03-25T14:58:18.471Z</wsu:Created>\n" +
            "         </wsse:UsernameToken>\n" +
            "         <wsu:Timestamp wsu:Id=\"TS-EC9F7473E024359C6A14589178984531\">\n" +
            "            <wsu:Created>2016-03-25T14:58:18.452Z</wsu:Created>\n" +
            "            <wsu:Expires>2016-03-25T15:03:18.452Z</wsu:Expires>\n" +
            "         </wsu:Timestamp>\n" +
            "      </wsse:Security>\n" +
            "   </SOAP-ENV:Header>";

    @Autowired
    private SoapParser parser;

    public SoapClient(String endpointUrl, Jaxb2Marshaller marshaller, ClientInterceptor... interceptors) {
        super();
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri(endpointUrl);
        setInterceptors(interceptors);
    }

    public ResponseWrapper<HeartBeatResponse> getHeartBeat(HeartBeatRequest request) {
        return marshalSendAndReceive(request);
    }

    public ResponseWrapper<Network> getNetwork(NetworkRequest request) {
        return marshalSendAndReceive(request);
    }

    public ResponseWrapper<StationList> getOrigins(OriginsRequest request) {
        return marshalSendAndReceive(request);
    }

    public ResponseWrapper<StationList> getDestinations(DestinationsRequest request) {
        return marshalSendAndReceive(request);
    }

    public ResponseWrapper<Trip> getTrip(TripRequest request) {
        return marshalSendAndReceive(request);
    }

    @SuppressWarnings("unchecked")
    private <Request, Response> ResponseWrapper<Response> marshalSendAndReceive(Request request) {
        ResponseWrapper<Response> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setRequestSize(parser.parseToContent(request).getBytes().length);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            responseWrapper.setResponse((Response) getWebServiceTemplate().marshalSendAndReceive(request));
        } finally {
            responseWrapper.setExecutionTimeInMillis(stopwatch.elapsed(MILLISECONDS));
            responseWrapper.setResponseSize(parser.parseToContent(responseWrapper.getResponse()).getBytes().length);
        }

        if (ArrayUtils.isNotEmpty(getInterceptors())) {
            responseWrapper.addRequestSize(EXAMPLE_HEADER.getBytes().length);
        }

        return responseWrapper;
    }
}
