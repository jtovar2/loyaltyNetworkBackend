# loyaltyNetworkBackend
A java servlet container that maps all incoming interactions between user, post, and groups to col statements. These interactions are created by users or maybe devices.

Exposed Apis:
  UserApi:
    AuthenticateUser: Path("/authuser/{username}/{password}") => returns boolean if username and password match
    
    AddUser: Path("/adduser") and a User instance in json => assigns a UUID to the User instance, and then adds it to database
    GetUser: Path("/getuser") and a EntityRef instance in json => uses the UUID inside the EntityRef, and fetches the User instance
    LookUpUser: Path("/lookupuser/{username}") => returns the EntityRef with the username
    
    
  GroupApi:
    AddFollower: Path("/addfollower") and a UserAndGroupRefContainer instance in json => adds user to group followers, and group to users affiliations
    AddMember: Path("/addmember") and a UserAndGroupRefContainer instance in json => adds user to group members, and group to users affiliations
    AddAdmin: Path("/addadmin") and a UserAndGroupRefContainer instance in json => adds user to group admins, and group to users affiliations
    SetLeader: Path("/setleader") and a UserAndGroupRefContainer instance in json => sets user as admin and adds group to user affiliations
    RemoveFollower: Path("/removefollower") and a UserAndGroupRefContainer instance in json => removes user from group followers, and group from users affiliations
    
    
