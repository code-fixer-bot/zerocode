package org.jsmart.zerocode.core.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.jsmart.zerocode.core.utils.TokenUtils.resolveKnownTokens;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

public class TokenUtilsTest {
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test
    public void testResolve_knownTokens() {
        String clientId = "zerocode-clientid_${RANDOM.NUMBER}";
        String uniqueClientId = resolveKnownTokens(clientId);
        String uniqueId = uniqueClientId.substring("zerocode-clientid_".length());

        assertThat(Long.parseLong(uniqueId) > 1, is(true));

    }	
    
    @Test
    public void testFixedRandomGenerator_success() {
    	IntStream.rangeClosed(1, 19).forEach(i->{
    		assertTrue(FixedRandomGenerator.getGenerator(i).generateRandomNumber().length() == i);
    	});
    }
    
    @Test
    public void testFixedRandomGenerator_failure_min() {
    	exceptionRule.expect(RuntimeException.class);
    	FixedRandomGenerator.getGenerator(0).generateRandomNumber();
    }
    
    @Test
    public void testFixedRandomGenerator_failure_max() {
    	exceptionRule.expect(RuntimeException.class);
    	FixedRandomGenerator.getGenerator(20).generateRandomNumber();
    }
    
    @Test
    public void testFixedRandomTokenReplace() {
    	 String clientId = "zerocode-clientid_${RANDOM.NUMBER:10}";
         String uniqueClientId = resolveKnownTokens(clientId);
         String uniqueId = uniqueClientId.substring("zerocode-clientid_".length());
         assertTrue(uniqueId.length() == 10);
    }
    
    @Test
    public void testFixedRandomUniqueness(){
    	String result = resolveKnownTokens("${RANDOM.NUMBER:12},${RANDOM.NUMBER:12}");
    	String[] split = result.split(",");
    	assertTrue(split[0] != split[1]);
    }
    
    
}



















