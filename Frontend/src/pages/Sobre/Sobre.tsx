import React from 'react';
import AboutUs from '../../components/AboutUs/AboutUs';
import Missao from '../../components/Missao/Missao';
import Equipe from '../../components/Equipe/Equipe';

const Sobre: React.FC = () => {
    return (
        <>
            <AboutUs />
            <Missao />
            <Equipe />
        </>
    );
};

export default Sobre;