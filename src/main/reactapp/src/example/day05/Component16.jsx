// 📦 컴포넌트 정의 시작

// npm install @mui/material @mui/styled-engine-sc styled-components
// npm install @mui/icons-material
import Button from '@mui/material/Button';
import Rating from '@mui/material/Rating';

import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import DraftsIcon from '@mui/icons-material/Drafts';

export default function Component16 ( props ){
    return(<>

        <h3> 버튼 </h3>
        <Button variant="outlined">MUI 버튼 </Button>
        <button> HTML 버튼 </button>

        <h3> 별 </h3>
        <Rating name="read-only" value={ 3 } readOnly />

        <h3> select </h3>
        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label"> 부서 </InputLabel>
            <Select labelId="demo-simple-select-label" id="demo-simple-select" label="부서"  >
            <MenuItem value = { "인사팀" } > 인사팀 </MenuItem>
            <MenuItem value = { "영업팀" } > 영업팀 </MenuItem>
            <MenuItem value = { "개발팀" }> 개발팀 </MenuItem>
            </Select>
        </FormControl>

        <h3> List </h3>

        {/* JSX 문법에서 주석처리 방법 */}
        {/* Box : div와 비슷하게 mui 레이아웃(구역) 을 구성할때 사용되는 컴포넌트 */}
        <Box sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
            <nav aria-label="main mailbox folders">
                { /* List : ul/ol 비슷하게 mui 에서 리스트(목록)구성할때 사용되는 컴포넌트 */}
                <List>
                { /* ListItem : li */}
                <ListItem disablePadding>
                    <ListItemButton>
                        <ListItemText primary="인사관리" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton>
                    <ListItemIcon>
                        <DraftsIcon />
                    </ListItemIcon>
                    <ListItemText primary="부품관리" />
                    </ListItemButton>
                </ListItem>
                </List>
            </nav>
            { /* Divider : <hr/> 처럼 구분선 지어주는 컴포넌트  */}
            <Divider />
        </Box>
        
    </>)
}

