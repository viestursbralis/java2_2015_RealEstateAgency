package lv.javaguru.java2.domain;


import javax.persistence.*;
import java.util.List;

/**
 * Created by Viesturs on 10/17/2015.
 */
@Entity
@Table(name="agent")
public class Agent {
    @Column(name="AGENT_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long agentId;
    @Column (name="AGENT_FIRST_NAME")
    private String agentFirstName;
    @Column(name="AGENT_LAST_NAME")
    private String agentLastName;
    @Column (name="AGENT_BIOGRAPHY")
    private String agentBiography;
    @Enumerated(EnumType.STRING)
    @Column(name="AGENT_STATUSS")
    private Statuss agentStatuss;
    @Column(name="AGENT_EMAIL")
    private String agentEmail;
    @Column(name="AGENT_PASSWORD")
    private String agentPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
    private List<User> agentUsers;


    public Long getAgentId() {
        return agentId;
    }
    public void setAgentId(Long id) {
        this.agentId = id;
    }

    public String getAgentFirstName() { return agentFirstName; }
    public void setAgentFirstName(String FirstName) {
        this.agentFirstName = FirstName;
    }

    public String getAgentLastName() {
        return agentLastName;
    }
    public void setAgentLastName(String LastName) {
        this.agentLastName = LastName;
    }

    public Statuss getAgentStatuss() {return agentStatuss;}
    public void setAgentStatuss (Statuss statuss) { this.agentStatuss=statuss;}

    public String getAgentBiography() {return agentBiography;}
    public void setAgentBiography (String agentBiography) { this.agentBiography=agentBiography;}

    public String getAgentPassword() {return agentPassword; }

    public void setAgentPassword(String agentPassword) { this.agentPassword = agentPassword; }

    public String getAgentEmail() {return agentEmail; }

    public void setAgentEmail(String agentEmail) { this.agentEmail = agentEmail; }

    public List<User> getAgentUsers() {return agentUsers; }

    public void setAgentUsers(List<User> users) {  this.agentUsers = users; }

    public String toString() {
        return "Agent FirstName: " + agentFirstName + ", LastName: "
                + agentLastName +", Id: "+ agentId+", Statuss: "+ agentStatuss ;
    }
/*******************************************************************************************************/
@Override
public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
        return false;
    }


    Agent agent = (Agent) obj;
    return  (((agentFirstName == agent.agentFirstName )|| (agentFirstName != null && agentFirstName.equals(agent.getAgentFirstName())))

            && ((agentLastName == agent.agentLastName )|| (agentLastName != null && agentLastName.equals(agent.getAgentLastName())))

            && ((agentEmail == agent.agentEmail )|| (agentEmail != null && agentEmail.equals(agent.getAgentEmail())))

            && ((agentPassword == agent.agentPassword )|| (agentPassword != null && agentPassword.equals(agent.getAgentPassword())))

            && ((agentBiography == agent.agentBiography )|| (agentBiography != null && agentBiography.equals(agent.getAgentBiography())))



            &&  ((agentStatuss == agent.agentStatuss )|| (agentStatuss != null && agentStatuss.equals(agent.getAgentStatuss())))  );
}
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((agentFirstName == null) ? 0 : agentFirstName.hashCode());

        result = prime * result
                + ((agentLastName == null) ? 0 : agentLastName.hashCode());

        result = prime * result
                + ((agentEmail == null) ? 0 : agentEmail.hashCode());

        result = prime * result
                + ((agentPassword == null) ? 0 : agentPassword.hashCode());

        result = prime * result
                + ((agentStatuss == null) ? 0 : agentStatuss.hashCode());



        result = prime * result
                + ((agentBiography == null) ? 0 : agentBiography.hashCode());

        return result;
    }



    /*****************************************************************************************************/




}
