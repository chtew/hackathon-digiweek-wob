import React from "react";
import {
    AppBar,
    Box,
    CssBaseline,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemButton,
    ListItemText,
    Stack,
    Toolbar,
    Typography
} from "@mui/material";
import {Logout} from "@mui/icons-material";
import HeaderStyles from "../../../assets/styles/HeaderStyles";
import {useTranslation} from "react-i18next";
import {useHistory} from "react-router-dom";

function SidebarNavigation(props) {

    const headerStyles = HeaderStyles();
    const drawerWidth = 240;
    const {t} = useTranslation();
    const history = useHistory();


    return (
        <Box sx={{display: 'flex'}}>
            <CssBaseline/>
            <AppBar position="fixed" sx={{zIndex: (theme) => theme.zIndex.drawer + 1}}>
                <Toolbar className={headerStyles.toolbar}>
                        <img className={headerStyles.menuLogoImg} src={props.logo} alt="Logo of lirejarp"/>
                        <Typography variant="h6" noWrap>
                            {props.title}
                        </Typography>
                    <div className={headerStyles.spacer}/>
                    <IconButton color="secondary" disableRipple className={headerStyles.linkButton}
                                onClick={() => history.push("/logout")}><Logout/></IconButton>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: {width: drawerWidth, boxSizing: 'border-box'},
                }}
            >
                <Toolbar/>
                <Box sx={{overflow: 'auto'}}>
                    <List>
                        {props.menuItems.map((menuItem, index) => (
                            <ListItem key={menuItem.title} disablePadding>
                                <ListItemButton onClick={() => history.push(menuItem.link)}>
                                    <ListItemText primary={t(menuItem.title)}/>
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>
            <Box component="main" sx={{flexGrow: 1, p: 3}}>
                <Toolbar className={headerStyles.toolbar}/>
                {props.children}
            </Box>
        </Box>
    )
}

export default SidebarNavigation;
