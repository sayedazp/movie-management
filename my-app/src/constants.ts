import { RoleTypes } from "./types";

export const rolesAccess:any =
    [
        {
            path:'home',
            text:'home',
            roles:[RoleTypes.ADMIN, RoleTypes.CLIENT]
        },
        {
            path:'manage-movies',
            text:'manage movies',
            roles:[RoleTypes.ADMIN, RoleTypes.CLIENT]
        },
        {
            path:'add-movies',
            text:'Add movies',
            roles:[RoleTypes.ADMIN]
        },
        
    ]
export const environment = {
    omdbApiKey: 'f1a699c2'
};
