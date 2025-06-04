import React from 'react';
import AboutUs from '../../components/AboutUs/AboutUs';
import Missao from '../../components/Missao/Missao';
import Equipe from '../../components/Equipe/Equipe';
import AltSobre from '../../components/AltSobre/AltSobre';
import AltProjects from '../../components/AltProjects/AltProjects';
import Impacto from '../../components/Impacto/Impacto';
import AltTestimonial from '../../components/AltTestimonial/AltTestimonial';
import Contato from '../../components/Contato/Contato';

const Sobre: React.FC = () => {
    return (
        <>
            <AboutUs />
            <Missao />
            <Equipe />
            <AltSobre />
            <AltProjects />
            <Impacto />
            <AltTestimonial />
            <Contato />
        </>
    );
};

export default Sobre;