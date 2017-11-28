
package businessLogic.impl.Maven.JSON;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import license.jacksonTemplate.LicenseSummary;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "licenseSummary"
})
public class LicenseJackson {

    @JsonProperty("licenseSummary")
    private LicenseSummary licenseSummary;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("licenseSummary")
    public LicenseSummary getLicenseSummary() {
        return licenseSummary;
    }

    @JsonProperty("licenseSummary")
    public void setLicenseSummary(LicenseSummary licenseSummary) {
        this.licenseSummary = licenseSummary;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
