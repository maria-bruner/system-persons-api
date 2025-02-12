import { Box } from '@mui/material';
import HomePersons from '../assets/HomePersons.png'

const Home = () => {
    return (
        <Box sx={{ 
            display: 'flex', 
            flexDirection: 'row', 
            justifyContent: 'center', 
            alignItems: 'center', 
            textAlign: 'center', 
            width: '70vw', 
            margin: '0 auto' 
        }}>
            <div style={{ 
                flex: 1, 
                backgroundColor: 'transparent', 
                backgroundImage: 'linear-gradient(0deg,rgb(129, 112, 255) 0%,rgb(252, 144, 193) 100%)',
                borderRadius: '20px'
            }}>
                <img 
                    src={HomePersons} 
                    alt='Logo que representa um sistema de pessoas'
                    style={{ width: '100%', height: '100%' }}
                />
            </div>
            <div style={{ flex: 1, padding: '20px' }}>
                <h1 style={{ color: '#422bff', fontSize: '60px' }}>Sistema People Slogan</h1>
                <p style={{ color: '#ff69ac', fontWeight: 'bold', fontSize: '30px' }}>Criado por quem entende os gargalos da logística</p>
            </div>
        </Box>
    )
}


export default Home