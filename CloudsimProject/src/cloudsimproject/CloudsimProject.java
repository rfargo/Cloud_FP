package cloudsimproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
        long cloudletFileSize = 300000;
        long cloudletOutputSize = 400000;
        UtilizationModelFull fullUtilize = new UtilizationModelFull();
        
        for (int cloudletId = 0; cloudletId < 178; cloudletId++) {
            Cloudlet newCloudlet = new Cloudlet(cloudletId, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize,
                    fullUtilize, fullUtilize, fullUtilize);
            newCloudlet.setUserId(dcb.getId());
            cloudletList.add(newCloudlet);
        }
        
        //5.0: Create VMs:Define the procedure for Task scheduling algorithm
        List<Vm> vmList = new ArrayList<Vm>();
        
        long diskSize = 256;
        int ram = 512;
        int mips = 250;
        int bandwith = 1000000;
        int vCPU = 1;
        String VMM = "XEN";
        
        for (int vmId = 0; vmId < 68; vmId++) {
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
            cloudletNo++;
        }
    }
    
    private static Datacenter CreateDataCenter() {
        List<Pe> peList = new ArrayList<Pe>();
        
        PeProvisionerSimple pProvisioner = new PeProvisionerSimple(10000);
        
		Pe core1 = new Pe(0, pProvisioner);
		peList.add(core1);
		Pe core2 = new Pe(1, pProvisioner);
		peList.add(core2);
		Pe core3 = new Pe(2, pProvisioner);
		peList.add(core3);
		Pe core4 = new Pe(3, pProvisioner);
		peList.add(core4);
		Pe core5 = new Pe(4, pProvisioner);
		peList.add(core5);
		Pe core6 = new Pe(5, pProvisioner);
		peList.add(core6);
		Pe core7 = new Pe(6, pProvisioner);
		peList.add(core7);
		Pe core8 = new Pe(7, pProvisioner);
		peList.add(core8);
		Pe core9 = new Pe(8, pProvisioner);
		peList.add(core9);
		Pe core10 = new Pe(9, pProvisioner);
		peList.add(core10);
		Pe core11 = new Pe(10, pProvisioner);
		peList.add(core11);
		Pe core12 = new Pe(11, pProvisioner);
		peList.add(core12);
		Pe core13 = new Pe(12, pProvisioner);
		peList.add(core13);
		Pe core14 = new Pe(13, pProvisioner);
		peList.add(core14);
		Pe core15 = new Pe(14, pProvisioner);
		peList.add(core15);
		Pe core16 = new Pe(15, pProvisioner);
		peList.add(core16);
		Pe core17 = new Pe(16, pProvisioner);
		peList.add(core17);
		Pe core18 = new Pe(17, pProvisioner);
		peList.add(core18);
		Pe core19 = new Pe(18, pProvisioner);
		peList.add(core19);
		Pe core20 = new Pe(19, pProvisioner);
		peList.add(core20);
		Pe core21 = new Pe(20, pProvisioner);
		peList.add(core21);
		Pe core22 = new Pe(21, pProvisioner);
		peList.add(core22);
		Pe core23 = new Pe(22, pProvisioner);
		peList.add(core23);
		Pe core24 = new Pe(23, pProvisioner);
		peList.add(core24);
		Pe core25 = new Pe(24, pProvisioner);
		peList.add(core25);
		Pe core26 = new Pe(25, pProvisioner);
		peList.add(core26);
		Pe core27 = new Pe(26, pProvisioner);
		peList.add(core27);
		Pe core28 = new Pe(27, pProvisioner);
		peList.add(core28);
		Pe core29 = new Pe(28, pProvisioner);
		peList.add(core29);
		Pe core30 = new Pe(29, pProvisioner);
		peList.add(core30);
		Pe core31 = new Pe(30, pProvisioner);
		peList.add(core31);
		Pe core32 = new Pe(31, pProvisioner);
		peList.add(core32);	
		Pe core33 = new Pe(32, pProvisioner);
		peList.add(core33);
		Pe core34 = new Pe(33, pProvisioner);
		peList.add(core34);	
        
        List<Host> hostlist = new ArrayList<Host>();
        
        int ram = 44500;
        int bw = 100000000;
        long storage = 10000000;
        Host host1 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host1);
        
        Host host2 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
        hostlist.add(host2);
        
//        Host host3 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
//        hostlist.add(host3);
//        
//        Host host4 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
//        hostlist.add(host4);
        
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
