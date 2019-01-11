package cloudsimproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
        
/**
 *
 * @author Johandy Jonarto, Reinaldy Fargo, Vincent Prawira
 */

public class CloudsimProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //1.0: Initialize the CloudSim package. It should be called before creating any entities.
        int numUser = 1;
        Calendar cal = Calendar.getInstance();
        boolean traceFlag = false;
        CloudSim.init(numUser, cal, traceFlag);
                
        //2.0: Create Datacenter: (Datacenter <<-- Datacentercharacteristics <<-- HostList K-- Processing element List),
        //     (Defines policy for VM allocation and scheduling)
        Datacenter dc = CreateDataCenter();
        
        //3.0: Create Broker
        DatacenterBroker dcb = null;
        try {
            dcb = new DatacenterBroker("DatacenterBroker1");
        } catch (Exception e){
            e.printStackTrace();
        }
        
        //4.0: Create Cloudlets:Defines the workload
        List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
        
        long cloudletLength = 40000;
        int pesNumber = 1;
        long cloudletFileSize = 300;
        long cloudletOutputSize = 400;
        UtilizationModelFull fullUtilize = new UtilizationModelFull();
        
        for (int cloudletId = 0; cloudletId < 40; cloudletId++) {
            Cloudlet newCloudlet = new Cloudlet(cloudletId, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize,
                    fullUtilize, fullUtilize, fullUtilize);
            newCloudlet.setUserId(dcb.getId());
            cloudletList.add(newCloudlet);
        }
        
        //5.0: Create VMs:Define the procedure for Task scheduling algorithm
        List<Vm> vmList = new ArrayList<Vm>();
        
        long diskSize = 20000;
        int ram = 2000;
        int mips = 1000;
        int bandwith = 1000;
        int vCPU = 1;
        String VMM = "XEN";
        
        for (int vmId = 0; vmId < 40; vmId++) {
            Vm virtualMachine = new Vm(vmId, dcb.getId(), mips, vCPU, ram, bandwith, diskSize, VMM, new CloudletSchedulerTimeShared());
            vmList.add(virtualMachine);
        }
        
        dcb.submitCloudletList(cloudletList);
        dcb.submitVmList(vmList);
        
        //6.0: Starts the simulation: Automated process, handled through descreted event simulation engine
        CloudSim.startSimulation();
        
        List<Cloudlet> finalCloudletExecutionResults = dcb.getCloudletReceivedList();
        
        CloudSim.stopSimulation();

        //7.0: Print results when simulation is over as Outputs
        int cloudletNo = 0;
        
        for (Cloudlet c : finalCloudletExecutionResults) {
            Log.printLine("Result of cloudlet No:" + cloudletNo);
            Log.printLine("*****************************************");
            Log.printLine("ID: " + c.getCloudletId() + ", VM: " + c.getVmId() + ", Status: " + c.getStatus()+ ", Submit: " + c.getActualCPUTime() + ", Start: " + c.getExecStartTime() + ", Finish: " + c.getFinishTime());
            Log.printLine("*****************************************");
        }
    }
    
    private static Datacenter CreateDataCenter() {
        List<Pe> peList = new ArrayList<Pe>();
        
        PeProvisionerSimple pProvisioner = new PeProvisionerSimple(1000);
        
        Pe core1 = new Pe(0, pProvisioner);
        peList.add(core1);
        Pe core2 = new Pe(1, pProvisioner);
        peList.add(core2);
        Pe core3 = new Pe(2, pProvisioner);
        peList.add(core3);
        Pe core4 = new Pe(3, pProvisioner);
        peList.add(core4);
        
        List<Host> hostlist = new ArrayList<Host>();
        
        int ram = 8000;
        int bw = 8000;
        long storage = 100000;
        Host host1 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host1);
        
        Host host2 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host2);
        
        Host host3 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host3);
        
        Host host4 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host4);
        
        String architecture = "x86";
        String os = "Linux";
        String vmm = "XEN";
        double timeZone = 5.0;
        double ComputecostPerSec = 3.0;
        double costPerMem = 1.0;
        double costPerStorage = 0.05;
        double costPerBw = 0.10;
        
        DatacenterCharacteristics dcCharacteristics = new DatacenterCharacteristics(architecture, os, vmm,
                hostlist, timeZone, ComputecostPerSec, costPerMem, costPerStorage, costPerBw);   
        
        LinkedList<Storage> SANstorage = new LinkedList<Storage>();
        
        Datacenter dc = null;
                
        try{
                dc = new Datacenter("Datacenter1", dcCharacteristics, new VmAllocationPolicySimple(hostlist), SANstorage, 1);    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dc;
    }
}
