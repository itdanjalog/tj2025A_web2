
import Button from '@mui/joy/Button';
import Box from '@mui/joy/Box';
import Input from '@mui/joy/Input';
import Select from '@mui/joy/Select';
import Option from '@mui/joy/Option';
import Switch from '@mui/joy/Switch';
import { useState } from 'react';

export default function Component14(props){

    // 3. 
      const handleChange = (event, newValue) => {
        alert(`You chose "${newValue}"`);
    };
    // 4. 
    const [checked, setChecked] = useState(false);

    return (<> 
        <h5> MUI 설치 : npm install @mui/joy @emotion/react @emotion/styled </h5>
        <p> 1. 소문자 마크업 : html , 대문자 마크업 : 컴포넌트( 다른 패키지이면 import ) </p>
        <button> html </button>
        <Button variant="solid">Hello world</Button>
        <p> 2. 마크업 속성 props 이란 : 마크업 안에 , 마크업 속성명=속성값  </p>

        <h1> 1. 버튼 : https://mui.com/joy-ui/react-button/ </h1>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
            <Button>Button</Button>
            <Button disabled>Disabled</Button>
            <Button loading>Loading</Button>
        </Box>
        
        <h1> 2. 입력상자 : https://mui.com/joy-ui/react-input/ </h1>
        <Input placeholder="Type in here…" />

        <h1> 3. 선택상자 : https://mui.com/joy-ui/react-select/ </h1>
        <Select defaultValue="dog" onChange={handleChange}>
            <Option value="dog">Dog</Option>
            <Option value="cat">Cat</Option>
            <Option value="fish">Fish</Option>
            <Option value="bird">Bird</Option>
        </Select>

        <h1> 4. 스위치on/off : https://mui.com/joy-ui/react-switch/  </h1>
        <Switch
            checked={checked}
            onChange={(event) => setChecked(event.target.checked)}
        />

    </>)
}