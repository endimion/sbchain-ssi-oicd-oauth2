pragma solidity >=0.4.21;



// This version assumes there exists only one issuer of VCs capable of revoking them
contract VcRevocationRegistry{

     //defines an entry to the VcRevocationRegistry
    struct Entry {
        bytes32 uuid;
        bool status;
        bool exists;
    }
    
    
    Entry[] entries; 
    mapping(bytes32 => uint) entiresUuidToIndex; 
    mapping(bytes32 => bool) uuidExists; 
    
    
    
    event RevokedEvent(uint indexed id, bytes32 uuid);
    event log(uint  id, bytes32 uuid, bool exists);
    event logExists(bytes32 uuid, bool exists);

    
    constructor() public {
         
   }
    
    
    function revoke(bytes32 _uuid) public{
        //add an entry
       entries.push(Entry(_uuid, false,true));
        uint  id = entries.length -1;
        emit RevokedEvent(id,_uuid);
        
        entiresUuidToIndex[_uuid] = id;
        uuidExists[_uuid] = true;
    } 
    
    
    function isRevoked(bytes32 _uuid) public  view returns (bool){
        // logExists(_uuid,uuidExists[_uuid]);
        if(entries.length == 0 || uuidExists[_uuid] == false){
          return false  ;
        }else{
            // log(entiresUuidToIndex[_uuid],_uuid, entries[entiresUuidToIndex[_uuid]].exists);
            
            return entries[entiresUuidToIndex[_uuid]].exists ;
        }
    }
    
    
    
}